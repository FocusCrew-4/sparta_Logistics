package com.keepgoing.order.infrastructure.persistence.repository.order;

import com.keepgoing.order.infrastructure.persistence.entity.order.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom {


}
