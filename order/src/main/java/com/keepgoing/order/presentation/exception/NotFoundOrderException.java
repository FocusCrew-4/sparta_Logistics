package com.keepgoing.order.presentation.exception;

public class NotFoundOrderException extends RuntimeException {

    public NotFoundOrderException(String message) {
        super(message);
    }
}
