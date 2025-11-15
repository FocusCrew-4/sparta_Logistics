package com.keepgoing.order.domain.vo;

import java.time.LocalDateTime;
import java.util.UUID;

public record Delivery(
    UUID deliveryId,
    LocalDateTime deliveryDueAt,
    String deliveryRequestNote
) {

}
