package com.keepgoing.delivery.delivery.infrastructure.persistence.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record RouteSeqJpa(
        @Column(name = "route_seq", nullable = false) int value
) {
}