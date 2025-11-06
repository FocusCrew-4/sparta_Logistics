package com.keepgoing.delivery.delivery.infrastructure.persistence.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public record AddressJpa(
        @Column(name = "street", nullable = false) String street,
        @Column(name = "city", nullable = false) String city,
        @Column(name = "zipcode", nullable = false) String zipcode
) {

}
