package com.keepgoing.order.domain.vo;

import java.util.Objects;
import java.util.UUID;

public record Vendor(
    UUID vendorId
) {

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
