package com.bowmeow.payment.dto;

import java.time.LocalDateTime;

/**
 * 결제 request DTO
 */
public record PaymentRequestDTO(String accessToken, LocalDateTime requestDate
        , LocalDateTime expiredDate, String key, String secret) {
    // TODO: 후에 ProductInfo, UserInfo 추가 필요(DTO 로?)
}
