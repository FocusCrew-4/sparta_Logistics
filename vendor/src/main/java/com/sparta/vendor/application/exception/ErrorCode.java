package com.sparta.vendor.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "000", "서버 내부 오류입니다."),
    FORBIDDEN_HUB_OPERATION(HttpStatus.FORBIDDEN, "8001","허브 관리자는 담당 허브에만 벤더를 등록할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
