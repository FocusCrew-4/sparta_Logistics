package com.keepgoing.order.application.event;

import com.keepgoing.order.application.service.payment.PaymentService;
import com.keepgoing.order.domain.order.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {

    private final PaymentService paymentService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("결제 처리 시작 - 주문 ID: {}", event.getOrderId());

        // 결제 처리 로직
        paymentService.processPayment(
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity()
        );

        log.info("결제 처리 완료 - 주문 ID: {}", event.getOrderId());
    }

    // @Async: 이 메서드를 별도의 스레드에서 실행합니다.
    // @TransactionalEventListener와 함께 사용하면,
    // 트랜잭션 커밋 후 비동기로 실행됩니다.
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedAsync(OrderCreatedEvent event) {
        // 로그로 현재 스레드 이름을 출력하여 비동기 실행을 확인합니다.
        log.info("비동기 결제 처리 시작 - Thread: {}, 주문 ID: {}",
            Thread.currentThread().getName(),
            event.getOrderId()
        );

        // 결제 처리 로직
        paymentService.processPayment(
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity()
        );

        log.info("비동기 결제 처리 완료 - 주문 ID: {}", event.getOrderId());
    }
}
