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
}
