package com.keepgoing.delivery.delivery.infrastructure.api.dto;

import java.util.UUID;

public record HubRouteResponse(
        UUID routeId,
        UUID departureHubId,
        UUID arrivalHubId,
        Double expectedDistance,
        Integer expectedTime
) {

}
