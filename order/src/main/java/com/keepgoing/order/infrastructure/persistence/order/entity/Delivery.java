package com.keepgoing.order.infrastructure.persistence.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Column(name = "delivery_id")
    private UUID deliveryId;

    @Column(name = "delivery_due_at", nullable = false)
    private LocalDateTime deliveryDueAt;

    @Column(name = "delivery_request_note")
    private String deliveryRequestNote;

    protected Delivery(UUID deliveryId, LocalDateTime deliveryDueAt, String deliveryRequestNote) {
        if (deliveryDueAt == null) throw new IllegalArgumentException("납품 기한은 필수값입니다.");
        this.deliveryId = deliveryId;
        this.deliveryDueAt = deliveryDueAt;
        this.deliveryRequestNote = deliveryRequestNote;
    }
}
