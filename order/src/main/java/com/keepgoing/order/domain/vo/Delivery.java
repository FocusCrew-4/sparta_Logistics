package com.keepgoing.order.domain.vo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record Delivery(
    UUID deliveryId,
    LocalDateTime deliveryDueAt,
    String deliveryRequestNote
) {

    public Delivery {
        if (deliveryDueAt == null) throw new IllegalArgumentException("납품 기한은 필수입니다.");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Delivery delivery = (Delivery) o;
        return Objects.equals(deliveryId, delivery.deliveryId) && Objects.equals(
            deliveryRequestNote, delivery.deliveryRequestNote) && Objects.equals(
            deliveryDueAt, delivery.deliveryDueAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, deliveryDueAt, deliveryRequestNote);
    }
}
