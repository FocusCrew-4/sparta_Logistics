package com.keepgoing.order.infrastructure.persistence.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Column(name="member_id", nullable = false)
    private Long memberId;

    protected Member(Long memberId) {
        if (memberId == null) throw new IllegalArgumentException("주문자 아이디는 필수입니다.");
        this.memberId = memberId;
    }
}
