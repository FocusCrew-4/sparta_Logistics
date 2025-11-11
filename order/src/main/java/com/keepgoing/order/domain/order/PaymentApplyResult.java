package com.keepgoing.order.domain.order;

public enum PaymentApplyResult {

    APPLIED("정상 처리"),
    ALREADY_PAID("이미 처리된 작업"),
    CANCELLED("결제 취소 보상 실행")
    ;

    private String description;

    PaymentApplyResult(String description) {
        this.description = description;
    }
}
