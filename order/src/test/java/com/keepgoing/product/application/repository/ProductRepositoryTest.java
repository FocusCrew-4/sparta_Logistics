package com.keepgoing.product.application.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.keepgoing.product.domain.Product;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.import=",
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false",
    }
)
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("제품을 등록하면 DB에 제품이 등록된다.")
    @Test
    void saveProduct() {
        // given
        Product product = Product.builder()
            .id(UUID.randomUUID())
            .name("무한동력기")
            .price(100000)
            .stock(100)
            .build();

        // when
        productRepository.save(product);

        Product refreshProduct = productRepository.findById(product.getId());
            // then
        Assertions.assertThat(refreshProduct.getId()).isEqualTo(product.getId());
    }
}