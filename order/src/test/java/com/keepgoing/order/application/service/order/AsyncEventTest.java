package com.keepgoing.order.application.service.order;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.keepgoing.order.application.dto.CreateOrderCommand;
import com.keepgoing.order.application.event.PaymentEventListener;
import com.keepgoing.order.domain.order.event.OrderCreatedEvent;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

// 3. 비동기 처리 테스트
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.cloud.config.enabled=false",
    "eureka.client.enabled=false"
})
@Slf4j
class AsyncEventTest {
    @Autowired
    private OrderEventPracticeService orderService;

    @MockitoBean
    private PaymentEventListener paymentEventListener;

    @Test
    @DisplayName("이벤트가 비동기로 처리된다")
    void events_ShouldBeProcessedAsynchronously() throws InterruptedException {
        // given
        CreateOrderCommand request = CreateOrderCommand.builder()
            .memberId(1L)
            .supplierId(UUID.randomUUID())
            .supplierName("오징어 집합체")
            .receiverId(UUID.randomUUID())
            .receiverName("오징어 모음체")
            .productId(UUID.randomUUID())
            .productName("오징어")
            .quantity(5)
            .price(10000000)
            .deliveryDueAt(LocalDateTime.now().plusDays(1))
            .deliveryRequestNote("오래걸려용")
            .build();

        String mainThread = Thread.currentThread().getName();
        log.info("메인 스레드: {}", mainThread);

        // when
        orderService.createOrder(request);

        // 비동기 처리를 위한 대기
        // 실무에서는 Awaitility 라이브러리를 사용하는 것이 더 안정적입니다.
//        Thread.sleep(1000);

        // then
//        verify(paymentEventListener, times(1))
//            .handleOrderCreatedAsync(any(OrderCreatedEvent.class));

        Awaitility.await()
            .atMost(2, TimeUnit.SECONDS)
            .untilAsserted(() ->
                verify(paymentEventListener).handleOrderCreatedAsync(any(OrderCreatedEvent.class))
            );

        // 로그를 확인하면 "event-async-" 접두사가 붙은 스레드 이름을 볼 수 있습니다.
        // 이를 통해 비동기로 실행되었음을 확인할 수 있습니다.
    }

    @Test
    @DisplayName("비동기 처리 중 예외가 발생해도 메인 로직에 영향을 주지 않는다")
    void whenAsyncEventThrowsException_MainFlowShouldNotBeAffected() {
        // given
        CreateOrderCommand request = CreateOrderCommand.builder()
            .memberId(1L)
            .supplierId(UUID.randomUUID())
            .supplierName("오징어 집합체")
            .receiverId(UUID.randomUUID())
            .receiverName("오징어 모음체")
            .productId(UUID.randomUUID())
            .productName("오징어")
            .quantity(5)
            .price(10000000)
            .deliveryDueAt(LocalDateTime.now().plusDays(1))
            .deliveryRequestNote("오래걸려용")
            .build();

        // Mock을 설정하여 결제 처리 중 예외가 발생하도록 합니다.
        doThrow(new RuntimeException("결제 실패"))
            .when(paymentEventListener)
            .handleOrderCreatedAsync(any(OrderCreatedEvent.class));

        // when & then
        // 비동기 작업에서 예외가 발생해도, 메인 로직(주문 생성)은 성공합니다.
        // 이는 사용자 경험 측면에서 중요합니다.
        assertThatCode(() -> orderService.createOrder(request))
            .doesNotThrowAnyException();

        // 다만 실무에서는 비동기 작업의 실패를 감지하고 대응하는 로직이 필요합니다.
        // 예: 재시도, 관리자 알림, 수동 처리를 위한 큐 저장 등
    }
}
