package com.sparta.practiceorder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.practiceorder.dto.OrderCreateRequest;
import com.sparta.practiceorder.events.OrderCreatedEvent;
import com.sparta.practiceorder.listener.PaymentEventListener;
import com.sparta.practiceorder.service.OrderService;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

@Slf4j
@SpringBootTest
public class AsyncEventTest {

    @Autowired
    private OrderService orderService;

    @MockitoSpyBean
    private PaymentEventListener paymentEventListener;

    @Test
    @DisplayName("이벤트가 비동기로 처리된다")
    void events_ShouldBeProcessedAsynchronously() throws InterruptedException {
        // given
        OrderCreateRequest request = createValidRequest();
        String mainThread = Thread.currentThread().getName();
        log.info("메인 스레드: {}", mainThread);

        // when
        orderService.createOrder(request);

        Thread.sleep(1000);

        // then
        verify(paymentEventListener, times(1))
            .handleOrderCreatedAsync(any(OrderCreatedEvent.class));

    }

    private OrderCreateRequest createValidRequest() {
        return OrderCreateRequest.builder()
            .productId(UUID.randomUUID())
            .build();
    }

    @Test
    @DisplayName("비동기 처리 중 예외가 발생해도 메인 로직에 영향을 주지 않는다")
    void whenAsyncEventThrowsException_MainFlowShouldNotBeAffected() {
        // given
        OrderCreateRequest request = createValidRequest();

        // Mock을 설정하여 결제 처리 중 예외가 발생하도록 합니다.
        doThrow(new RuntimeException("결제 실패"))
            .when(paymentEventListener)
            .handleOrderCreatedAsync(any(OrderCreatedEvent.class));

        // when & then
        assertThatCode(() -> orderService.createOrder(request))
            .doesNotThrowAnyException();

    }
}
