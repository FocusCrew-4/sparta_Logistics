package com.keepgoing.order.application.service;

import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.domain.Order;
import com.keepgoing.order.infrastructure.OrderRepository;
import com.keepgoing.order.presentation.dto.response.CreateOrderResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

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
}