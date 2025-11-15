package com.keepgoing.order.application.event;

import com.keepgoing.order.domain.order.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("주문 생성 이벤트 수신 - 주문 ID: {}, 상품 ID: {}, 수량: {}",
            event.getOrderId(),
            event.getProductId(),
            event.getQuantity()
        );
        // 실무에서는 여기서 알림 발송, 로그 저장 등의 작업을 수행합니다.
    }
}
