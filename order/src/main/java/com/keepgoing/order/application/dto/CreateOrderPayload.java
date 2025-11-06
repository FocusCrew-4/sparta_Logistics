package com.keepgoing.order.application.dto;


import com.keepgoing.order.domain.OrderState;
import java.util.UUID;

public record CreateOrderPayload (
    UUID orderId,
    UUID receiverId,
    UUID productId,
    OrderState state
) {

}
