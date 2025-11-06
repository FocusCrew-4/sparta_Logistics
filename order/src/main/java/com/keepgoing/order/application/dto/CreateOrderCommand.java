package com.keepgoing.order.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.keepgoing.order.domain.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateOrderCommand (
    @JsonProperty("supplier_id")
    UUID supplierId,
    @JsonProperty("supplier_name")
    String supplierName,
    @JsonProperty("receiver_id")
    UUID receiverId,
    @JsonProperty("receiver_name")
    String receiverName,
    @JsonProperty("product_id")
    UUID productId,
    @JsonProperty("product_name")
    String productName,
    @JsonProperty("quantity")
    Integer quantity,
    @JsonProperty("total_price")
    Integer totalPrice,
    @JsonProperty("delivery_due_at")
    LocalDateTime deliveryDueAt,
    @JsonProperty("delivery_request_note")
    String deliveryRequestNote
){
    @Override
    public UUID supplierId() {
        return supplierId;
    }

    @Override
    public String supplierName() {
        return supplierName;
    }

    @Override
    public UUID receiverId() {
        return receiverId;
    }

    @Override
    public String receiverName() {
        return receiverName;
    }

    @Override
    public UUID productId() {
        return productId;
    }

    @Override
    public String productName() {
        return productName;
    }

    @Override
    public Integer quantity() {
        return quantity;
    }

    @Override
    public Integer totalPrice() {
        return totalPrice;
    }

    @Override
    public LocalDateTime deliveryDueAt() {
        return deliveryDueAt;
    }

    @Override
    public String deliveryRequestNote() {
        return deliveryRequestNote;
    }

    public Order toEntity() {

        if (supplierId == null) throw new IllegalArgumentException("공급 업체의 식별자는 필수값입니다.");
        if (supplierName == null || supplierName.isBlank()) throw new IllegalArgumentException("공급 업체 이름은 필수 값입니다.");
        if (receiverId == null) throw new IllegalArgumentException("수령 업체의 식별자는 필수값입니다.");
        if (receiverName == null || receiverName.isBlank()) throw new IllegalArgumentException("수령 업체 이름은 필수 값입니다.");
        if (productId == null) throw new IllegalArgumentException("상품의 식별자는 필수값입니다.");
        if (productName == null || productName.isBlank()) throw new IllegalArgumentException("상품 이름은 필수 값입니다.");
        if (quantity == null) throw new IllegalArgumentException("상품 수량은 필수값입니다.");
        if (totalPrice == null) throw new IllegalArgumentException("총 가격은 필수값입니다.");
        if (deliveryDueAt == null) throw new IllegalArgumentException("납품 기간은 필수값입니다.");
        ;
        LocalDateTime now = LocalDateTime.now();

        return Order.create(
            supplierId, supplierName, receiverId, receiverName, productId, productName,
            quantity, totalPrice, now, deliveryDueAt ,deliveryRequestNote
        );
    }
}
