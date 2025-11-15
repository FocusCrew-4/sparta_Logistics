package com.keepgoing.order.domain.model;

import com.keepgoing.order.domain.state.CancelState;
import com.keepgoing.order.domain.state.OrderState;
import com.keepgoing.order.domain.vo.Delivery;
import com.keepgoing.order.domain.vo.Hub;
import com.keepgoing.order.domain.vo.Member;
import com.keepgoing.order.domain.vo.Product;
import com.keepgoing.order.domain.vo.Vendor;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.springframework.security.core.parameters.P;

public class Order {

    private UUID id;

    private Member member;

    private Vendor supplier;

    private Vendor receiver;

    private Product product;

    private Hub hub;

    private Delivery delivery;

    private Integer quantity;

    private Integer totalPrice;

    private OrderState orderState;

    private CancelState cancelState;

    private UUID idempotencyKey;

    private LocalDateTime orderedAt;

    private LocalDateTime confirmedAt;

    private LocalDateTime cancelledAt;

    @Builder
    private Order(Member member, Vendor supplier, Vendor receiver, Product product, Hub hub,
        Delivery delivery, Integer quantity, Integer totalPrice, OrderState orderState,
        CancelState cancelState, UUID idempotencyKey, LocalDateTime orderedAt) {
        this.member = member;
        this.supplier = supplier;
        this.receiver = receiver;
        this.product = product;
        this.hub = hub;
        this.delivery = delivery;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderState = orderState;
        this.cancelState = cancelState;
        this.idempotencyKey = idempotencyKey;
        this.orderedAt = orderedAt;
        this.confirmedAt = null;
        this.cancelledAt = null;
    }

    public static Order create(Long memberId, UUID supplierId, UUID receiverId, UUID productId, UUID hubId,
        LocalDateTime deliveryDueAt, String deliveryRequestNote, Integer quantity, Integer totalPrice, UUID idempotencyKey,
        LocalDateTime now) {

        Member member = new Member(memberId);
        Vendor supplier = new Vendor(supplierId);
        Vendor receiver = new Vendor(receiverId);
        Product product = new Product(productId);
        Hub hub = new Hub(hubId);
        Delivery delivery = new Delivery(null, deliveryDueAt, deliveryRequestNote);

        if (quantity < 1) throw new IllegalArgumentException("주문 수량은 1개 이상이어야 합니다.");
        if (totalPrice < 1000) throw new IllegalArgumentException("총 주문 금액은 1000원 이상이어야 합니다.");
        if (idempotencyKey == null) throw new IllegalArgumentException("멱등키는 필수입니다.");
        if (now == null) throw new IllegalArgumentException("현재 날짜는 필수입니다.");

        return Order.builder()
            .member(member)
            .supplier(supplier)
            .receiver(receiver)
            .product(product)
            .hub(hub)
            .delivery(delivery)
            .quantity(quantity)
            .totalPrice(totalPrice)
            .orderState(OrderState.PENDING_VALIDATION)
            .cancelState(CancelState.NONE)
            .idempotencyKey(idempotencyKey)
            .orderedAt(now)
            .build();
    }
}
