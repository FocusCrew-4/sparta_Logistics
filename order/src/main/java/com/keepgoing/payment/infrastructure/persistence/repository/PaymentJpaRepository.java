package com.keepgoing.payment.infrastructure.persistence.repository;

import com.keepgoing.payment.domain.Payment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByOrderId(UUID orderId);
}
