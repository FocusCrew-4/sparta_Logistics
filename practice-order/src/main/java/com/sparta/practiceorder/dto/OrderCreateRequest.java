package com.sparta.practiceorder.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreateRequest {

    UUID productId;
    UUID supplierId;
    UUID receiverId;
    int quantity;

}
