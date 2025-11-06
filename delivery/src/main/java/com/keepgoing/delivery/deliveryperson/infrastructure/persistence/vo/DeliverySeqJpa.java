package com.keepgoing.delivery.deliveryperson.infrastructure.persistence.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record DeliverySeqJpa(
        @Column(name = "delivery_seq", nullable = false)
        int value
) {
}