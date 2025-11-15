package com.keepgoing.order.application.repository;

import com.keepgoing.order.domain.model.Order;

public interface OrderRepository {

    Order save(Order order);

}
