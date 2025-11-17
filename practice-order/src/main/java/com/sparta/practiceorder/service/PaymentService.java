package com.sparta.practiceorder.service;

import com.sparta.practiceorder.entity.Payment;
import com.sparta.practiceorder.events.PaymentCompletedEvent;
import com.sparta.practiceorder.repository.PaymentRepository;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void processPayment(UUID orderId, UUID productId, int quantity) {
        // 간단한 결제 처리 로직
        Payment payment = Payment.create(orderId, calculateAmount(productId, quantity));

        if (quantity == 300) {
            throw new RuntimeException();
        }

        payment.complete(); // 실무에서는 PG사 연동 등의 복잡한 로직이 들어갑니다

        paymentRepository.save(payment);

        // 결제 완료 이벤트 발행
        eventPublisher.publishEvent(PaymentCompletedEvent.of(payment, orderId, productId, quantity));
    }

    private BigDecimal calculateAmount(UUID productId, int quantity) {
        return new BigDecimal(quantity).multiply(new BigDecimal(100));
    }
}

