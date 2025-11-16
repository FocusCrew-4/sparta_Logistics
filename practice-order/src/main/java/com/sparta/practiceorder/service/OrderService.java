package com.sparta.practiceorder.service;

import com.sparta.practiceorder.dto.OrderCreateRequest;
import com.sparta.practiceorder.dto.OrderResponse;
import com.sparta.practiceorder.entity.Order;
import com.sparta.practiceorder.events.OrderCreatedEvent;
import com.sparta.practiceorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {

        Order order = Order.create(
            request.getProductId(),
            request.getSupplierId(),
            request.getReceiverId(),
            request.getQuantity()
        );
        Order savedOrder = orderRepository.save(order);

        eventPublisher.publishEvent(OrderCreatedEvent.of(savedOrder));

        return OrderResponse.from(savedOrder);
    }
}

