package com.keepgoing.delivery.deliveryperson.infrastructure.persistence.entity;

import com.keepgoing.delivery.deliveryperson.domain.entity.*;
import com.keepgoing.delivery.deliveryperson.infrastructure.persistence.vo.DeliverySeqJpa;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "delivery_persons")
public class DeliveryPersonEntity {

    @Id
    private Long userId; // 사용자 관리 엔티티와 동일한 ID 사용

    @Column(nullable = false)
    private UUID hubId;

    @Column(nullable = false)
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryPersonTypeJpa type;

    @Embedded
    private DeliverySeqJpa deliverySeq;

}
