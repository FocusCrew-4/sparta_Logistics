package com.keepgoing.payment.application.repo;

import com.keepgoing.payment.domain.Payment;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository {

    Payment findById(UUID paymentId);

    Payment save(Payment payment);

    List<Payment> findByOrderId(UUID orderId);

}
