package com.keepgoing.order.application.service.order;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.application.event.OrderEventListener;
import com.keepgoing.order.domain.order.Order;
import com.keepgoing.order.domain.order.event.OrderCreatedEvent;
import com.keepgoing.order.infrastructure.order.OrderRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = OrderEventPracticeService.class)
@TestPropertySource(properties = {
    "spring.cloud.config.enabled=false",
    "eureka.client.enabled=false"
})
@ActiveProfiles("test")
class OrderEventPracticeServiceTest {

    @Autowired
    private OrderEventPracticeService orderService;

    @MockitoBean
    private OrderEventListener orderEventListener;

    @MockitoBean
    private OrderRepository orderRepository;

    @Test
    @DisplayName("주문 생성 시 OrderCreatedEvent가 발행된다")
    void createOrder_ShouldPublishOrderCreatedEvent() {
        // given
        CreateOrderCommand request = CreateOrderCommand.builder()
            .memberId(1L)
            .supplierId(UUID.randomUUID())
            .supplierName("오징어 집합체")
            .receiverId(UUID.randomUUID())
            .receiverName("오징어 모음체")
            .productId(UUID.randomUUID())
            .productName("오징어")
            .quantity(5)
            .price(10000000)
            .deliveryDueAt(LocalDateTime.now().plusDays(1))
            .deliveryRequestNote("오래걸려용")
            .build();


        // when
        // save 호출 시 order를 그대로 반환하도록 Mock
        Order order = request.toEntity();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        orderService.createOrder(request);

        // then
        // 이벤트 리스너가 호출되었는지 검증
        verify(orderEventListener, times(1))
            .handleOrderCreated(any(OrderCreatedEvent.class));
    }
}
