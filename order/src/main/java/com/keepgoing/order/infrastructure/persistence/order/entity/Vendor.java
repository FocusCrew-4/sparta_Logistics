package com.keepgoing.order.infrastructure.persistence.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vendor {
    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    protected Vendor(UUID vendorId) {
        if (vendorId == null) throw new IllegalArgumentException("업체의 아이디는 필수입니다.");
        this.vendorId = vendorId;
    }
}
