package com.keepgoing.order.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keepgoing.order.application.dto.CreateOrderPayload;
import com.keepgoing.order.application.exception.InventoryReservationFailException;
import com.keepgoing.order.application.exception.NotFoundProductException;
import com.keepgoing.order.domain.AggregateType;
import com.keepgoing.order.domain.EventType;
import com.keepgoing.order.domain.Order;
import com.keepgoing.order.domain.OrderOutbox;
import com.keepgoing.order.domain.OrderState;
import com.keepgoing.order.infrastructure.OrderOutboxRepository;
import com.keepgoing.order.infrastructure.OrderRepository;
import com.keepgoing.order.presentation.HubClient;
import com.keepgoing.order.presentation.ProductClient;
import com.keepgoing.order.presentation.dto.request.ReservationInventoryRequest;
import com.keepgoing.order.presentation.dto.response.ProductInfo;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "OrderProcessor")
@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;
    private final ProductClient productClient;
    private final HubClient hubClient;
    private final ObjectMapper objectMapper;
    private final Clock clock;

    @Async("task-worker")
    @Transactional
    public void processTask(UUID orderId) {
        Order order = orderRepository.findOrderForUpdate(orderId)
            .orElse(null);

        if (order == null) {
            log.error("주문을 찾지 못함 {}", orderId);
            return;
        }

        try {
            switch (order.getOrderState()) {
                case OrderState.PENDING_VALIDATION :
                    log.info("상품 유효성 검증 수행 {}", orderId);

                    String productId = order.getProductId().toString();
                    ProductInfo productInfo = productClient.getProduct(productId);

                    if (productInfo == null) throw new NotFoundProductException("해당 상품을 찾을 수 없습니다.");

                    UUID hubId = UUID.fromString(productInfo.getHubId());
                    order.changeOrderStateToProductVerified();
                    order.registerHubId(hubId);

                    break;

                case OrderState.PRODUCT_VERIFIED :
                    log.info("재고 예약 수행 {}", orderId);

                    String productIdForInventory = String.valueOf(order.getProductId());
                    String hubIdForInventory = order.getHubId().toString();
                    Integer quantity = order.getQuantity();
                    String idempotencyKey = order.getIdempotencyKey().toString();

                    Boolean result = hubClient.reservationInventoryForProduct(
                        ReservationInventoryRequest.create(productIdForInventory, hubIdForInventory, quantity, idempotencyKey)
                    );

                    if (result == false) throw new InventoryReservationFailException("재고 예약에 실패했습니다.");
                    order.changeOrderStateToAwaitingPayment();

                    break;
                case OrderState.AWAITING_PAYMENT :
                    // TODO: 결제 시스템을 도입한다면 결제 API 호출 필요
                    log.info("결제 요청 수행 {}", orderId);

                    order.changeOrderStateToPaid();
                    break;

                case OrderState.PAID :
                    log.info("알림을 위한 Outbox에 데이터 등록 {}", orderId);
                    String payload = makePayload(order);

                    OrderOutbox outbox = OrderOutbox.create(
                        UUID.randomUUID(),
                        AggregateType.ORDER,
                        order.getId().toString(),
                        EventType.COMPLETED,
                        payload,
                        LocalDateTime.now(clock)
                    );

                    orderOutboxRepository.save(outbox);

                    order.changeOrderStateToCompleted();
                    break;
            }

        } catch (NotFoundProductException | InventoryReservationFailException orderFailException) {
            log.error("주문을 처리하는 도중 문제 발생 {}", orderId, orderFailException);
            order.changeOrderStateToFail();

        } catch (Exception e) {
            order.changeOrderStateToFail();
        }
    }

    private String makePayload(Order order) throws JsonProcessingException {
        CreateOrderPayload createOrderPayload = new CreateOrderPayload(
            order.getId(),
            order.getReceiverId(),
            order.getProductId(),
            order.getOrderState()
        );

        return objectMapper.writeValueAsString(createOrderPayload);
    }
}
