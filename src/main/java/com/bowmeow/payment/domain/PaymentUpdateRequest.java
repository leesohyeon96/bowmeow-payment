package com.bowmeow.payment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentUpdateRequest {
    private Integer paymentId;
    private String impUid;
    private String merchantUid;
    // 그뭐야 데이터 다 들고와야햄
}
