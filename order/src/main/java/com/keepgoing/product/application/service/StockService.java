package com.keepgoing.product.application.service;

import com.keepgoing.product.application.repository.ProductRepository;
import com.keepgoing.product.domain.InventoryReservedEvent;
import com.keepgoing.product.domain.Product;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "StockService")
@Service
@RequiredArgsConstructor
public class StockService {
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public void decreaseStock(UUID orderId, UUID productId, int quantity) {
        Product product = productRepository.findById(productId);

        product.decreaseStock(quantity);

        productRepository.save(product);

        eventPublisher.publishEvent(InventoryReservedEvent.of(
            orderId,
            productId,
            LocalDateTime.now(clock)
        ));

        // 재고 차감 완료 로그
        log.info("재고 차감 완료 - 상품 ID: {}, 차감 수량: {}", productId, quantity);
    }
}
