package com.keepgoing.order.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.application.repository.OrderRepository;
import com.keepgoing.order.domain.event.OrderCreatedEvent;
import com.keepgoing.order.domain.model.Order;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest2 {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @Mock
    Clock clock;

    @InjectMocks
    OrderService orderService;


    @DisplayName("주문 생성시 이벤트가 주문 생성 이벤트가 발행된다.")
    @Test
    void orderCreateTest() {
        // given
        Instant fixedInstant = Instant.parse("2025-10-11T00:00:00Z");

        Mockito.when(clock.instant()).thenReturn(fixedInstant);
        Mockito.when(clock.getZone()).thenReturn(ZoneId.of("UTC"));

        UUID supplierId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID receiverId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID productId  = UUID.fromString("33333333-3333-3333-3333-333333333333");
        UUID idempotencyKey = UUID.fromString("44444444-4444-4444-4444-444444444444");

        CreateOrderCommand command = CreateOrderCommand.builder()
            .memberId(1L)
            .supplierId(supplierId)
            .supplierName("공급업체A")
            .receiverId(receiverId)
            .receiverName("수령자B")
            .productId(productId)
            .productName("상품C")
            .quantity(1)
            .price(10000)
            .deliveryDueAt(LocalDateTime.now().plusDays(1))
            .deliveryRequestNote("문 앞에 놔주세요")
            .build();

        Mockito.when(orderRepository.save(Mockito.any()))
            .thenReturn(
                Order.create(
                    1L,
                    supplierId,
                    receiverId,
                    productId,
                    LocalDateTime.of(2025, 11, 1, 0, 0, 0),
                    "문 앞에 놔주세요.",
                    1000,
                    50000,
                    idempotencyKey,
                    LocalDateTime.now()
                )
            );

        // when
        orderService.createOrder(command);

        // then
        Mockito.verify(applicationEventPublisher, Mockito.times(1))
            .publishEvent(Mockito.any(OrderCreatedEvent.class));
    }
}