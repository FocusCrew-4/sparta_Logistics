package com.keepgoing.order.application.service;

import com.keepgoing.order.application.exception.InventoryReservationFailException;
import com.keepgoing.order.application.exception.NotFoundProductException;
import com.keepgoing.order.application.lock.StepLock;

import com.keepgoing.order.domain.Order;
import com.keepgoing.order.presentation.HubClient;
import com.keepgoing.order.presentation.ProductClient;
import com.keepgoing.order.presentation.dto.request.ReservationInventoryRequest;
import com.keepgoing.order.presentation.dto.response.ProductInfo;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j(topic = "OrderProcessor")
@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderService orderService;
    private final ProductClient productClient;
    private final HubClient hubClient;
    private final StepLock stepLock;

    private static final String STEP_VALIDATE_PRODUCT = "VALIDATE_PRODUCT";
    private static final String STEP_RESERVE_STOCK = "RESERVE_STOCK";
    private static final String STEP_PAYMENT = "PAYMENT";
    private static final String STEP_COMPLETE = "COMPLETE";

    @Value("${lock.local.ttl-seconds}")
    Long ttl;

    @Async("taskExecutor")
    public void processTask(UUID orderId) {
        Order order = orderService.findById(orderId);

        if (order == null) {
            log.error("주문을 찾지 못함 {}", orderId);
            return;
        }

        try {
            switch (order.getOrderState()) {
                case PENDING_VALIDATION :
                    log.info("상품 유효성 검증 수행 {}", orderId);

                    UUID idForProductValidation = order.getId();
                    String lockKeyForProductValidation = getLockKey(idForProductValidation, STEP_VALIDATE_PRODUCT);

                    if (!stepLock.tryAcquire(lockKeyForProductValidation, Duration.ofSeconds(ttl))) {
                        log.debug("이미 처리 중인 작업: {}", lockKeyForProductValidation);
                        return;
                    }

                    try {
                        String productId = order.getProductId().toString();
                        ProductInfo productInfo = productClient.getProduct(productId);

                        if (productInfo == null || productInfo.getHubId() == null){
                            orderService.toFail(orderId, order.getVersion());
                            return;
                        }

                        UUID hubId = null;
                        try {
                            hubId = UUID.fromString(productInfo.getHubId());
                        } catch (IllegalArgumentException ex) {
                            orderService.toFail(orderId, order.getVersion());
                            return;
                        }

                        orderService.toProductVerified(orderId, order.getVersion(), hubId);
                    } finally {
                        stepLock.release(lockKeyForProductValidation);
                    }

                    break;

                case PRODUCT_VERIFIED :
                    log.info("재고 예약 수행 {}", orderId);

                    UUID idForStockReservation = order.getId();
                    String lockKeyForStockReservation = getLockKey(idForStockReservation, STEP_RESERVE_STOCK);

                    if (!stepLock.tryAcquire(lockKeyForStockReservation, Duration.ofSeconds(ttl))) {
                        log.debug("이미 처리 중인 작업: {}", lockKeyForStockReservation);
                        return;
                    }

                    try {
                        String productIdForInventory = String.valueOf(order.getProductId());
                        String hubIdForInventory = order.getHubId().toString();
                        Integer quantity = order.getQuantity();
                        String idempotencyKey = order.getIdempotencyKey().toString();

                        boolean result = hubClient.reservationInventoryForProduct(
                            ReservationInventoryRequest.create(productIdForInventory, hubIdForInventory, quantity, idempotencyKey)
                        );

                        if (result == false) {
                            orderService.toFail(orderId, order.getVersion());
                            return;
                        }

                        orderService.toAwaitingPayment(orderId, order.getVersion());
                    } finally {
                        stepLock.release(lockKeyForStockReservation);
                    }
                    break;
                case AWAITING_PAYMENT :
                    log.info("결제 요청 수행 {}", orderId);

                    UUID idForPayment = order.getId();
                    String lockKeyForPayment = getLockKey(idForPayment, STEP_PAYMENT);

                    if (!stepLock.tryAcquire(lockKeyForPayment, Duration.ofSeconds(ttl))) {
                        log.debug("이미 처리 중인 작업: {}", lockKeyForPayment);
                        return;
                    }

                    try {
                        // TODO: 결제 시스템을 도입한다면 결제 API 호출 필요 - 결제는 무조건 실행된다고 본다.

                        orderService.toPaid(orderId, order.getVersion());
                    } finally {
                        stepLock.release(lockKeyForPayment);
                    }
                    break;

                case PAID :
                    log.info("배송을 위한 Outbox에 데이터 등록 {}", orderId);

                    UUID idForOrderCompleted = order.getId();
                    String lockKeyForOrderCompleted = getLockKey(idForOrderCompleted, STEP_COMPLETE);

                    if (!stepLock.tryAcquire(lockKeyForOrderCompleted, Duration.ofSeconds(ttl))) {
                        log.debug("이미 처리 중인 작업: {}", lockKeyForOrderCompleted);
                        return;
                    }

                    try {
                        orderService.toCompleted(orderId, order.getVersion());
                    } finally {
                        stepLock.release(lockKeyForOrderCompleted);
                    }

                    break;
            }

        } catch (NotFoundProductException | InventoryReservationFailException orderFailException) {
            log.error("주문을 처리하는 도중 문제 발생 {}", orderId, orderFailException);

        } catch (Exception e) {
            log.error("주문을 처리 실패 {}", orderId, e);
        }
    }

    private static String getLockKey(UUID orderId, String step) {
        return ("lock:order:" + orderId + ":" + step).toLowerCase();
    }

}