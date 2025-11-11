package com.keepgoing.order.infrastructure.order;

import com.keepgoing.order.domain.order.CancelState;
import com.keepgoing.order.domain.order.Order;
import com.keepgoing.order.domain.order.OrderState;
import java.time.LocalDateTime;
import java.util.Collection;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    // query
    Page<UUID> findPendingIds(Collection<OrderState> states, Pageable pageable);

    Page<Order> searchOrderPage(Pageable pageable);

    Optional<OrderState> findOrderStateById(UUID orderId);

    Page<UUID> findByCancelState(CancelState state, Collection<OrderState> orderStates, Pageable pageable);

    Page<UUID> findPendingCancelIds(Collection<CancelState> cancelStates, Collection<OrderState> orderStates, Pageable pageable);

    // command
    int claim(UUID orderId, OrderState beforeState, OrderState afterState, LocalDateTime now);

    int updateOrderStateToProductVerifiedWithHub(UUID orderId, UUID hubId, LocalDateTime now);

    int updateOrderStateToAwaitingPayment(UUID orderId, LocalDateTime now);

    int updateOrderStateToPaid(UUID orderId, LocalDateTime now);

    int updateOrderStateToCompleted(UUID orderId, LocalDateTime now);

    int updateOrderStateToPaidForPayment(UUID orderId, Long version, LocalDateTime now);

    int deleteOrder(UUID orderId, Long memberId, LocalDateTime now, Long version);

    int updateCancelState(UUID orderId, OrderState orderState, CancelState state, LocalDateTime now);

    int updateCancelRequired(UUID orderId, OrderState orderState, LocalDateTime now);

    int cancelClaim(UUID orderId, Collection<OrderState> orderStates, CancelState beforeCancelState,
        CancelState afterCancelState, LocalDateTime now);

    int updateCancelStateToOrderCancelled(UUID orderId, LocalDateTime now);

    int updateCancelStateToOrderCancelAwaiting(UUID orderId, LocalDateTime now);

    int updateCancelStateToInventoryReservationCancelAwaiting(UUID orderId, LocalDateTime now);

    int revertPaymentCancelToAwaiting(UUID orderId, LocalDateTime now);

    int revertInventoryReservationCancelToAwaiting(UUID orderId, LocalDateTime now);

}
