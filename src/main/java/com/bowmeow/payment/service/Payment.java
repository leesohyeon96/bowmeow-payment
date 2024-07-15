package com.bowmeow.payment.service;

import com.bowmeow.payment.domain.PaymentRequest;
import com.bowmeow.payment.domain.PaymentResponse;

public interface Payment {

    PaymentResponse pay(PaymentRequest payment);

    // 결제 취소(=환불)

    // 결제 이력 조회
}
