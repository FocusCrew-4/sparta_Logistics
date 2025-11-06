package com.keepgoing.delivery.deliveryperson.domain.service;

import com.keepgoing.delivery.deliveryperson.domain.entity.DeliveryPerson;
import com.keepgoing.delivery.deliveryperson.domain.entity.DeliveryPersonType;
import com.keepgoing.delivery.deliveryperson.domain.entity.DeliverySeq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class DeliveryPersonDomainServiceTest {

    private final DeliveryPersonService service = new DeliveryPersonService();

    @Test
    @DisplayName("새로운 허브 배송 담당자는 기존 마지막 순번 다음으로 배정된다")
    void assignNextHubSeq_ShouldBeLastPlusOne() {
        // given
        int currentCount = 2;  // 이미 2명 존재
        int maxSeq = 2;        // 마지막 순번은 2

        // when
        DeliveryPerson newPerson = service.assignNextHubSeq(
                DeliveryPerson.createHubDeliveryPerson(103L, "SLACK-103", null),
                currentCount,
                maxSeq
        );

        // then
        assertThat(newPerson.getDeliverySeq().value()).isEqualTo(3);
    }

    @Test
    @DisplayName("허브 배송 담당자가 최대 인원 10명을 초과하면 예외 발생")
    void assignNextHubSeq_MaxExceeded_ShouldThrow() {
        // given
        int currentCount = 10;
        int maxSeq = 10;

        // when & then
        assertThatThrownBy(() -> service.assignNextHubSeq(
                DeliveryPerson.createHubDeliveryPerson(111L, "SLACK-111", null),
                currentCount,
                maxSeq
        )).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("허브 배송 담당자 최대 인원(10명)을 초과할 수 없습니다.");
    }

    @Test
    @DisplayName("새로운 업체 배송 담당자는 해당 허브의 마지막 순번 다음으로 배정된다")
    void assignNextVendorSeq_ShouldBeLastPlusOnePerHub() {
        // given
        UUID hubA = UUID.randomUUID();
        int currentCount = 2;
        int maxSeq = 2;

        // when
        DeliveryPerson newVendor = service.assignNextVendorSeq(
                DeliveryPerson.createVendorDeliveryPerson(203L, hubA, "SLACK-203", null),
                currentCount,
                maxSeq,
                hubA
        );

        // then
        assertThat(newVendor.getDeliverySeq().value()).isEqualTo(3);
    }

    @Test
    @DisplayName("업체 배송 담당자가 허브별 최대 인원 10명을 초과하면 예외 발생")
    void assignNextVendorSeq_MaxExceeded_ShouldThrow() {
        // given
        UUID hubB = UUID.randomUUID();
        int currentCount = 10;
        int maxSeq = 15; // 이미 순번이 15까지 배정된 상태 (삭제된 사람 있을 수 있음)

        // when & then
        assertThatThrownBy(() -> service.assignNextVendorSeq(
                DeliveryPerson.createVendorDeliveryPerson(311L, hubB, "SLACK-311", null),
                currentCount,
                maxSeq,
                hubB
        )).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("해당 허브의 업체 배송 담당자 최대 인원(10명)을 초과할 수 없습니다.");
    }
}
