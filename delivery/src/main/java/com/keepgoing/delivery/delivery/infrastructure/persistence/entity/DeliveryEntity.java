package com.keepgoing.delivery.delivery.infrastructure.persistence.entity;

import com.keepgoing.delivery.delivery.domain.entity.*;
import com.keepgoing.delivery.delivery.infrastructure.persistence.vo.AddressJpa;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deliveries")
public class DeliveryEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatusJpa status;

    @Column(nullable = false)
    private UUID departureHubId;

    @Column(nullable = false)
    private UUID destinationHubId;

    @Embedded
    private AddressJpa address;

    @Column(nullable = false)
    private Long recipientUserId;

    @Column(nullable = false)
    private String recipientSlackId;

    private Long vendorDeliveryPersonId;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryRouteEntity> routes = new ArrayList<>();



}
