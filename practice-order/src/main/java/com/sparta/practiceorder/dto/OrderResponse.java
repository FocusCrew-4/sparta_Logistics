package com.sparta.practiceorder.dto;

import com.sparta.practiceorder.entity.Order;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {

    UUID id;

    public static OrderResponse from(Order savedOrder) {
        return new OrderResponse(
            savedOrder.getId()
        );
    }
}
