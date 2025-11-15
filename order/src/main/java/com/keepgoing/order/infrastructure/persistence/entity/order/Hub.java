package com.keepgoing.order.infrastructure.persistence.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub {
    @Column(name = "hub_id")
    private UUID hubId;
}
