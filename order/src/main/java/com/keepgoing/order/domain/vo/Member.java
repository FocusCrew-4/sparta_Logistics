package com.keepgoing.order.domain.vo;

import java.util.Objects;

public record Member(
    Long memberId
){

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberId);
    }
}
