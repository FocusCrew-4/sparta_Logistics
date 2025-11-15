package com.keepgoing.order.domain.vo;

import java.util.Objects;
import java.util.UUID;

public record Product (
    UUID productId
) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }
}
