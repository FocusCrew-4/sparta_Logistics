package com.keepgoing.order.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ProductSearchResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private ProductInfo productInfo;

    @JsonProperty("error")
    private ErrorCode errorCode;

    public boolean fail() {
        return !success;
    }
}
