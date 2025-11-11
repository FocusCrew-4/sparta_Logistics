package com.sparta.hub.routes.infrastructure.repository;

import com.sparta.hub.routes.domain.entity.HubRoute;
import com.sparta.hub.routes.domain.repository.HubRouteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaHubRouteRepository extends JpaRepository<HubRoute, UUID>, HubRouteRepository {
    Optional<HubRoute> findByOriginHubIdAndDestinationHubId(UUID originHubId, UUID destinationHubId);
}