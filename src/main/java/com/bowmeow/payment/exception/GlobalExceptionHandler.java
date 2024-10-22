package com.bowmeow.payment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // PayFailedException 처리
    @ExceptionHandler(PaymentFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public PaymentFailedException handlerPaymentFailedException(PaymentFailedException e) {
        log.error("PaymentFailedException occured={}", e.getMessage());
        return new PaymentFailedException(e.getMessage());
    }
}
