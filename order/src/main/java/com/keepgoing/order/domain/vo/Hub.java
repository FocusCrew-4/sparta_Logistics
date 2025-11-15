package com.keepgoing.order.domain.vo;

import java.util.Objects;
import java.util.UUID;

public record Hub(
    UUID hubId
) {

    public Hub {
        if (hubId == null) throw new IllegalArgumentException("허브 아이디는 필수입니다.");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hub hub = (Hub) o;
        return Objects.equals(hubId, hub.hubId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hubId);
    }
}
