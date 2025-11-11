package com.keepgoing.order.application.exception;

public class OrderCancelFailException extends RuntimeException{

    public OrderCancelFailException(String message) {
        super(message);
    }
}
