package com.keepgoing.delivery.delivery.infrastructure.persistence.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record DistanceJpa(
        @Column(name = "distance_km", nullable = false) double km
) {
}