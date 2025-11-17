package com.keepgoing.payment.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCompletedEvent {
    private final UUID orderId;
    private final UUID productId;
    private final Integer quantity;
    private final LocalDateTime paidAt;

    public static PaymentCompletedEvent of(UUID orderId, UUID productId, Integer quantity, LocalDateTime paidAt) {
        return new PaymentCompletedEvent(
            orderId,
            productId,
            quantity,
            paidAt
        );
    }
}
