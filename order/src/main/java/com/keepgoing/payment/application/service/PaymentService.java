package com.keepgoing.payment.application.service;

import com.keepgoing.payment.domain.PaymentCompletedEvent;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "PaymentService")
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public void processPayment(UUID orderId, UUID productId, Integer totalPrice, Integer quantity) {
        log.info("결제 처리 프로세스 실행 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 레코드 DB에 등록 요청 주문 ID {}, 상품 ID {}", orderId, productId);
        // 결제 레코드 DB에 등록 (생략)
        log.info("결제 레코드 DB에 저장 완료 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 PG사에 결제 요청 주문 ID {}, 상품 ID {}", orderId, productId);
        // PG 결제 (생략)
        log.info("결제 PG사 결제 성공 실행 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 결과 DB에 반영 시도 주문 ID {}, 상품 ID {}", orderId, productId);
        // PG 결제 결과 DB에 반영 (생략)
        log.info("결제 결과 DB에 반영 완료 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 완료 이벤트 발행 시도 {}", orderId);
        eventPublisher.publishEvent(PaymentCompletedEvent.of(
            orderId, productId, quantity,
            LocalDateTime.now(clock)));

        log.info("결제 완료 이벤트 발행 완료 {}", orderId);
    }

    @Transactional
    public void processPaymentForAsync(UUID orderId, UUID productId, Integer totalPrice, Integer quantity) {
        log.info("결제 처리 프로세스 실행 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 레코드 DB에 등록 요청 주문 ID {}, 상품 ID {}", orderId, productId);
        // 결제 레코드 DB에 등록 (생략)
        log.info("결제 레코드 DB에 저장 완료 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 PG사에 결제 요청 주문 ID {}, 상품 ID {}", orderId, productId);
        // PG 결제 (생략)
        log.info("결제 PG사 결제 성공 실행 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 결과 DB에 반영 시도 주문 ID {}, 상품 ID {}", orderId, productId);
        // PG 결제 결과 DB에 반영 (생략)
        log.info("결제 결과 DB에 반영 완료 주문 ID {}, 상품 ID {}", orderId, productId);


        log.info("결제 완료 이벤트 발행 시도 {}", orderId);
        eventPublisher.publishEvent(PaymentCompletedEvent.of(
            orderId, productId, quantity,
            LocalDateTime.now(clock)));

        log.info("결제 완료 이벤트 발행 완료 {}", orderId);
    }
}
