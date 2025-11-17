package com.keepgoing.order.application.listener;

import com.keepgoing.order.application.service.OrderService;
import com.keepgoing.product.domain.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j(topic = "InventoryReservedListener")
@Component
@RequiredArgsConstructor
public class InventoryReservedListener {

    private final OrderService orderService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleInventoryReserved(InventoryReservedEvent event) {
        log.info("재고 예약 후, 주문 상태 변경 시작 orderId : {}", event.getOrderId());

        orderService.updateOrderStateToCompleted(event.getOrderId());

        log.info("재고 예약 후, 주문 상태 변경 완료 orderId : {}", event.getOrderId());
    }
}
