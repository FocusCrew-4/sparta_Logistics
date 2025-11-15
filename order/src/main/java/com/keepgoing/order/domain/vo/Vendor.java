package com.keepgoing.order.domain.vo;

import java.util.Objects;
import java.util.UUID;

public record Vendor(
    UUID vendorId
) {
    public Vendor {
        if (vendorId == null) throw new IllegalArgumentException("업체 아이디는 필수입니다.");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vendor vendor = (Vendor) o;
        return Objects.equals(vendorId, vendor.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vendorId);
    }
}
