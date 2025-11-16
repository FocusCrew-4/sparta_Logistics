//package com.keepgoing.order.domain.model;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.keepgoing.order.domain.vo.Delivery;
//import com.keepgoing.order.domain.vo.Hub;
//import com.keepgoing.order.domain.vo.Product;
//import com.keepgoing.order.domain.vo.Vendor;
//import java.time.LocalDateTime;
//import java.util.UUID;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class OrderTest {
//
//
//    @DisplayName("주문을 생성할 때 memberId가 null이라면 IllegalArgumentException 예외가 발생하며 주문 도메인 생성에 실패한다.")
//    @Test
//    void createOrderFailWhenMemberIdIsNull() {
//
//        // given
//        Vendor supplier = new Vendor(
//            UUID.fromString("5cfb8c2c-4c40-48a4-9e87-1f6b0d282f32")
//        );
//
//        Vendor receiver = new Vendor(
//            UUID.fromString("8c1c3b63-7dfb-4c2f-987d-b5421fa8ef7e")
//        );
//
//        Product product = new Product(
//            UUID.fromString("d5bb2f27-e9f9-4d22-af91-0f1a0b8c9ad5")
//        );
//
//        Hub hub = new Hub(
//            UUID.fromString("3ae1c6f4-89e9-4fd0-890d-71e13e48e6f7")
//        );
//
//        Delivery delivery = new Delivery(
//            null,
//            LocalDateTime.of(2025, 11, 15, 20, 38, 0),
//            "정각에 배달 부탁드립니다."
//        );
//
//        UUID idempotencyKey = UUID.fromString("e72d5379-c97d-4777-8437-0bd966819c56");
//
//        Order order = Order.builder()
//            .member(null)
//            .supplier(supplier)
//            .receiver(receiver)
//            .product(product)
//            .hub(hub)
//            .delivery(delivery)
//            .
//            .build()
//
//        // when
//
//        // then
//    }
//}