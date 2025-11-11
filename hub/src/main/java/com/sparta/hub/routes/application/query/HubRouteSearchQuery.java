package com.sparta.hub.routes.application.query;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public record HubRouteSearchQuery(
        UUID originHubId,
        UUID destinationHubId,
        Integer maxTransitMinutes,
        BigDecimal maxDistanceKm,
        Pageable pageable
) {}