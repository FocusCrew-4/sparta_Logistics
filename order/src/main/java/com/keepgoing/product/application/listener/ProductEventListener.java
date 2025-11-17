package com.keepgoing.product.application.listener;

import com.keepgoing.payment.domain.PaymentCompletedEvent;
import com.keepgoing.product.application.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j(topic = "ProductEventListener")
@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final StockService stockService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("결제 완료 이벤트 수신 orderId = {}", event.getOrderId());

        stockService.decreaseStock(
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity()
        );

        log.info("재고 차감 완료 orderId = {}, productId = {}"
            , event.getOrderId(), event.getProductId());
    }

}
