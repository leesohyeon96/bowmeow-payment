package com.bowmeow.payment.util;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAIT("대기"),
    PAID("지불");

    // 필드에 접근하기 위한 getter 메서드
    private final String status;

    // 생성자 추가
    OrderStatus(String status) {
        this.status = status;
    }

}
