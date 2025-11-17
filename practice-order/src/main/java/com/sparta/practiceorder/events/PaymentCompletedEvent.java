package com.sparta.practiceorder.events;

import com.sparta.practiceorder.entity.Payment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public class PaymentCompletedEvent {

    private final UUID paymentId;
    private final UUID orderId;
    private final UUID productId;
    private final Integer quantity;


    public static Object of(Payment payment, UUID orderId, UUID productId, Integer quantity) {
        return new PaymentCompletedEvent(payment.getId(), orderId, productId, quantity);
    }
}
