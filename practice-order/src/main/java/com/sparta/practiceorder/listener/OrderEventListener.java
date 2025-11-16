package com.sparta.practiceorder.listener;

import com.sparta.practiceorder.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("주문 생성 이벤트 수신 - 주문 ID: {}, 상품 ID: {}, 수량: {}, 호출메서드: {}",
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity(),
            event.getTriggeredBy()
        );
    }

}
