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
public class Product {
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    protected Product(UUID productId) {
        if (productId == null) throw new IllegalArgumentException("상품의 아이디는 필수입니다.");
        this.productId = productId;
     }
}
