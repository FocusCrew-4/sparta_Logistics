package com.keepgoing.order.presentation.dto.response.api;

import com.keepgoing.order.domain.model.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateOrderResponse (
    String orderId,
    Long memberId,
    String supplierId,
    String receiverId,
    String productId,
    String deliveryId,
    String deliveryDueAt,
    String deliveryRequestNote,
    Integer quantity,
    Integer totalPrice,
    String orderedAt
) {

    public static CreateOrderResponse from(Order order) {

        return CreateOrderResponse.builder()
            .orderId(String.valueOf(order.getId()))
            .memberId(order.getMember().memberId())
            .supplierId(String.valueOf(order.getSupplier().vendorId()))
            .receiverId(String.valueOf(order.getReceiver().vendorId()))
            .productId(String.valueOf(order.getProduct().productId()))
            .deliveryId(String.valueOf(order.getDelivery().deliveryId()))
            .deliveryDueAt(String.valueOf(order.getDelivery().deliveryDueAt()))
            .deliveryRequestNote(order.getDelivery().deliveryRequestNote())
            .quantity(order.getQuantity())
            .totalPrice(order.getTotalPrice())
            .orderedAt(String.valueOf(order.getOrderedAt()))
            .build();
    }
}
