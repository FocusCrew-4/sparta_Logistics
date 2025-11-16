package com.sparta.practiceorder.dto;

import com.sparta.practiceorder.entity.Order;
import lombok.Data;

@Data
public class OrderResponse {


    public static OrderResponse from(Order savedOrder) {
        return new OrderResponse();
    }
}
