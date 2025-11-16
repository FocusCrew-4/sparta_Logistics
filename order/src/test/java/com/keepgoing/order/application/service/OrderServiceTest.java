package com.keepgoing.order.application.service;

import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.application.listener.OrderCreatedEventListener;
import com.keepgoing.order.domain.event.OrderCreatedEvent;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
    properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.import=",
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false",
    }
)
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockitoBean
    private OrderCreatedEventListener orderCreatedEventListener;

    @DisplayName("주문 생성 시 OrderCreateEvent가 발행된다.")
    @Test
    void createOrderTest() {
        // given
        CreateOrderCommand command = CreateOrderCommand.builder()
            .memberId(1L)
            .supplierId(UUID.randomUUID())
            .supplierName("공급업체A")
            .receiverId(UUID.randomUUID())
            .receiverName("수령자B")
            .productId(UUID.randomUUID())
            .productName("상품C")
            .quantity(1)
            .price(10000)
            .deliveryDueAt(LocalDateTime.now().plusDays(1))
            .deliveryRequestNote("문 앞에 놔주세요")
            .build();

        // when
        orderService.createOrder(command);

        // then
        Mockito.verify(orderCreatedEventListener, Mockito.times(1))
            .handleOrderCreadted(Mockito.any(OrderCreatedEvent.class));
    }
}