package com.keepgoing.order.application.service.order;

import com.keepgoing.order.domain.order.CancelState;
import com.keepgoing.order.domain.order.Order;
import com.keepgoing.order.domain.order.OrderState;
import com.keepgoing.order.infrastructure.order.OrderRepository;
import java.time.Clock;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "CancelRequestMapper")
@Service
@RequiredArgsConstructor
public class CancelRequestMapper {

    private final OrderRepository orderRepository;
    private final Clock clock;

    @Transactional
    public void mapRequestToAwaiting(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.isNotCancelRequested()) return;

        OrderState orderState = order.getOrderState();
        CancelState state = switch (order.getOrderState()) {
            case PRODUCT_VERIFIED -> CancelState.ORDER_CANCEL_AWAITING;
            case AWAITING_PAYMENT -> CancelState.INVENTORY_RESERVATION_CANCEL_AWAITING;
            case PAID, COMPLETED -> CancelState.PAYMENT_CANCEL_AWAITING;
            default -> null;
        };

        if (state == null) return;

        int updated = orderRepository.updateCancelState(orderId, orderState, state, LocalDateTime.now(clock));
        if (updated == 0) {
            log.debug("취소 매핑 실패, 상태가 바뀌거나 다른 곳에서 이미 처리 {} {}", orderId, state);
        }
    }
}
