package com.keepgoing.product.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryReservedEvent {

    private final UUID orderId;
    private final UUID productId;
    private final LocalDateTime reservedAt;

    public static InventoryReservedEvent of(UUID orderId, UUID productId, LocalDateTime reservedAt) {
        return new InventoryReservedEvent(
            orderId,
            productId,
            reservedAt
        );
    }
}
