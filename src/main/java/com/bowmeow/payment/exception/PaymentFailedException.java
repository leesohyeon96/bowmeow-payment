package com.bowmeow.payment.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentFailedException extends RuntimeException {
    private String message;
}
