package com.keepgoing.order.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {

    @JsonProperty("product_id")
    private String productId;


    @JsonProperty("hub_id")
    private String hubId;

    @JsonProperty("product_name")
    private String productName;

}
