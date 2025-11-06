package com.keepgoing.order.infrastructure;

import com.keepgoing.order.domain.Order;
import com.keepgoing.order.domain.OrderState;
import java.util.Collection;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<UUID> findPendingIds(Collection<OrderState> states, Pageable pageable);

    Optional<Order> findOrderForUpdate(UUID orderId);
}
