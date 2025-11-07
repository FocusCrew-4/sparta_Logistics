package com.keepgoing.order.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.application.dto.CreateOrderPayloadForDelivery;
import com.keepgoing.order.application.dto.CreateOrderPayloadForNotification;
import com.keepgoing.order.domain.AggregateType;
import com.keepgoing.order.domain.EventType;
import com.keepgoing.order.domain.Order;
import com.keepgoing.order.domain.OrderOutbox;
import com.keepgoing.order.domain.OrderState;
import com.keepgoing.order.domain.OutBoxState;
import com.keepgoing.order.infrastructure.OrderOutboxRepository;
import com.keepgoing.order.infrastructure.OrderRepository;
import com.keepgoing.order.presentation.dto.response.CreateOrderResponse;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "OrderService")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;
    private final Clock clock;


    @Transactional
    public CreateOrderResponse create(CreateOrderCommand createOrderCommand) {

        Order order = createOrderCommand.toEntity();
        orderRepository.save(order);

        return CreateOrderResponse.create(
            order.getId(),
            order.getOrderState(),
            order.getOrderedAt()
        );
    }

    public Order findById(UUID orderId) {
        return orderRepository.findById(orderId)
            .orElse(null);
    }

    @Transactional
    public void toProductVerified(UUID orderId, Long version, UUID hubId) {
        // FIXME: 낙관적 락 적용되는지 확인 필요
        Order order = orderRepository.findById(orderId).orElseThrow();

        ensureVersion(order, version);

        if (order.getOrderState() != OrderState.PENDING_VALIDATION) {
            log.error("상품 유효성 검증 상태 전이 실패 {}", orderId);
            return;
        }

        order.registerHubId(hubId);
        order.changeOrderStateToProductVerified();
    }

    @Transactional
    public void toAwaitingPayment(UUID orderId, Long version) {
        // FIXME: 낙관적 락 적용되는지 확인 필요
        Order order = orderRepository.findById(orderId).orElseThrow();

        ensureVersion(order, version);

        if (order.getOrderState() != OrderState.PRODUCT_VERIFIED) {
            log.error("재고 예약 상태 전이 실패 {}", orderId);
            return;
        }

        order.changeOrderStateToAwaitingPayment();
    }

    @Transactional
    public void toPaid(UUID orderId, Long version) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        ensureVersion(order, version);

        if (order.getOrderState() != OrderState.AWAITING_PAYMENT) {
            log.error("결제 완료 상태 전이 실패 {}", orderId);
            return;
        }

        String payloadForNotification = makePayloadForNotification(order);

        OrderOutbox outboxForNotification = OrderOutbox.create(
            UUID.randomUUID(),
            AggregateType.ORDER,
            order.getId().toString(),
            EventType.PAID,
            OutBoxState.NOTIFICATION_PENDING,
            payloadForNotification,
            LocalDateTime.now(clock)
        );

        orderOutboxRepository.save(outboxForNotification);

        order.changeOrderStateToPaid();
    }

    @Transactional
    public void toCompleted(UUID orderId, Long version) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        ensureVersion(order, version);

        if (order.getOrderState() != OrderState.PAID) {
            log.error("주문 완료 상태 전이 실패 {}", orderId);
            return;
        }

        String payloadForDelivery = makePayloadForDelivery(order);

        OrderOutbox outboxForDelivery = OrderOutbox.create(
            UUID.randomUUID(),
            AggregateType.ORDER,
            order.getId().toString(),
            EventType.COMPLETED,
            OutBoxState.DELIVERY_PENDING,
            payloadForDelivery,
            LocalDateTime.now(clock)
        );

        orderOutboxRepository.save(outboxForDelivery);
        order.changeOrderStateToCompleted();
    }

    @Transactional
    public void toFail(UUID orderId, Long version) {
        // FIXME: 낙관적 락 적용되는지 확인 필요
        Order order = orderRepository.findById(orderId)
            .orElseThrow();
        if (order.getOrderState() == OrderState.FAILED) return;
        order.changeOrderStateToFail();

        // TODO: 실패 후 후속처리 필요 (Outbox 혹은 다른 방식 적용)
    }

    private void ensureVersion(Order order, long expected) {
        if (!order.getVersion().equals(expected)){
            throw new IllegalStateException("낙관적 락 실패 : " + order.getId());
        }
    }

    private String makePayloadForNotification(Order order) {
        CreateOrderPayloadForNotification createOrderPayloadForNotification = new CreateOrderPayloadForNotification(
            order.getId(),
            order.getSupplierName(),
            order.getReceiverName(),
            order.getProductName(),
            order.getQuantity(),
            order.getOrderedAt(),
            order.getDeliveryDueAt(),
            order.getDeliveryRequestNote()
        );

        try {
            return objectMapper.writeValueAsString(createOrderPayloadForNotification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON으로 변환하지 못했습니다.");
        }
    }

    private String makePayloadForDelivery(Order order) {
        CreateOrderPayloadForDelivery createOrderPayloadForDelivery = new CreateOrderPayloadForDelivery(
            order.getId(),
            order.getReceiverId(),
            order.getProductId(),
            order.getOrderState()
        );

        try {
            return objectMapper.writeValueAsString(createOrderPayloadForDelivery);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON으로 변환하지 못했습니다.");
        }
    }
}