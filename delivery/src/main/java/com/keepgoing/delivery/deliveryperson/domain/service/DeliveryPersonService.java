package com.keepgoing.delivery.deliveryperson.domain.service;

import com.keepgoing.delivery.deliveryperson.domain.entity.DeliveryPerson;
import com.keepgoing.delivery.deliveryperson.domain.entity.DeliveryPersonType;
import com.keepgoing.delivery.deliveryperson.domain.entity.DeliverySeq;

import java.util.UUID;

public class DeliveryPersonService {

    /**
     * 허브 배송 담당자 순번 배정
     *
     * @param newPerson   새로 등록할 배송 담당자
     * @param currentCount 현재 허브 배송 담당자 수
     * @param maxSeqValue 현재까지 배정된 최대 순번 (없으면 null)
     */
    public DeliveryPerson assignNextHubSeq(
            DeliveryPerson newPerson,
            int currentCount,
            Integer maxSeqValue
    ) {
        if (newPerson.getType() != DeliveryPersonType.HUB) {
            throw new IllegalArgumentException("허브 배송 담당자만 이 메서드를 사용할 수 있습니다.");
        }

        if (currentCount >= 10) {
            throw new IllegalStateException("허브 배송 담당자 최대 인원(10명)을 초과할 수 없습니다.");
        }

        int nextSeq = (maxSeqValue == null ? 1 : maxSeqValue + 1);

        return DeliveryPerson.createHubDeliveryPerson(
                newPerson.getUserId(),
                newPerson.getSlackId(),
                new DeliverySeq(nextSeq)
        );
    }

    /**
     * 업체 배송 담당자 순번 배정 (허브별)
     *
     * @param newPerson   새로 등록할 업체 배송 담당자
     * @param hubId       소속 허브 ID
     * @param currentCount 해당 허브의 업체 배송 담당자 수
     * @param maxSeqValue 해당 허브에서의 최대 순번 (없으면 null)
     */
    public DeliveryPerson assignNextVendorSeq(
            DeliveryPerson newPerson,
            int currentCount,
            Integer maxSeqValue,
            UUID hubId
    ) {
        if (newPerson.getType() != DeliveryPersonType.VENDOR) {
            throw new IllegalArgumentException("업체 배송 담당자만 이 메서드를 사용할 수 있습니다.");
        }

        if (currentCount >= 10) {
            throw new IllegalStateException("해당 허브의 업체 배송 담당자 최대 인원(10명)을 초과할 수 없습니다.");
        }

        int nextSeq = (maxSeqValue == null ? 1 : maxSeqValue + 1);

        return DeliveryPerson.createVendorDeliveryPerson(
                newPerson.getUserId(),
                hubId,
                newPerson.getSlackId(),
                new DeliverySeq(nextSeq)
        );
    }
}
