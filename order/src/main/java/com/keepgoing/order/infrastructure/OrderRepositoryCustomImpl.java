package com.keepgoing.order.infrastructure;

import static com.keepgoing.order.domain.QOrder.*;

import com.keepgoing.order.domain.Order;
import com.keepgoing.order.domain.OrderState;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UUID> findPendingIds(Collection<OrderState> states, Pageable pageable) {

        List<UUID> content = queryFactory
            .select(order.id)
            .from(order)
            .where(order.orderState.in(states))
            .orderBy(order.createdAt.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(order.count())
            .from(order)
            .where(order.orderState.in(states));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Order> findOrderForUpdate(UUID orderId) {
        Order result = queryFactory
            .selectFrom(order)
            .where(order.id.eq(orderId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .setHint("jakarta.persistence.lock.timeout", "SKIP_LOCKED")
            .fetchOne();

        return Optional.of(result);
    }
}
