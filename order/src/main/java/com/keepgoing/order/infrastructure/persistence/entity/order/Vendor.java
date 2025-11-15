package com.keepgoing.order.infrastructure.persistence.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vendor {
    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;
}
