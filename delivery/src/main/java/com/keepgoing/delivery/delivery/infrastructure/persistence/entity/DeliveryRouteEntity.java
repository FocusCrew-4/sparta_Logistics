package com.keepgoing.delivery.delivery.infrastructure.persistence.entity;

import com.keepgoing.delivery.delivery.domain.entity.*;
import com.keepgoing.delivery.delivery.domain.vo.RouteSeq;
import com.keepgoing.delivery.delivery.infrastructure.persistence.vo.DistanceJpa;
import com.keepgoing.delivery.delivery.infrastructure.persistence.vo.DurationJpa;
import com.keepgoing.delivery.delivery.infrastructure.persistence.vo.RouteSeqJpa;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "delivery_routes")
public class DeliveryRouteEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    private RouteSeqJpa routeSeq;

    @Column(nullable = false)
    private UUID departureHubId;

    @Column(nullable = false)
    private UUID arrivalHubId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "km", column = @Column(name = "expected_distance"))
    })
    private DistanceJpa expectedDistance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minutes", column = @Column(name = "expected_time"))
    })
    private DurationJpa expectedTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "km", column = @Column(name = "actual_distance"))
    })
    private DistanceJpa actualDistance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "minutes", column = @Column(name = "actual_time"))
    })
    private DurationJpa actualTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryRouteStatusJpa status;

    private Long deliveryPersonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;


}
