package com.keepgoing.order.infrastructure.persistence.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Column(name="member_id", nullable = false)
    private Long memberId;
}
