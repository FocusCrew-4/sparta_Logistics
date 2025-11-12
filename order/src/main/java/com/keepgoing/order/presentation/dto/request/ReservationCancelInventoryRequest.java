package com.keepgoing.order.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelInventoryRequest {
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("hub_id")
    private String hubId;

    private ReservationCancelInventoryRequest(String productId, String hubId) {
        this.productId = productId;
        this.hubId = hubId;
    }

    public static ReservationCancelInventoryRequest create(String productId, String hubId) {
        return new ReservationCancelInventoryRequest(productId, hubId);
    }
}
