package com.keepgoing.delivery.delivery.infrastructure.persistence.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record DurationJpa(
        @Column(name = "duration_minutes", nullable = false) int minutes
) {
}