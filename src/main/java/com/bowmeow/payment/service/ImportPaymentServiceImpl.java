package com.bowmeow.payment.service;

import com.bowmeow.payment.domain.PaymentRequest;
import com.bowmeow.payment.domain.PaymentResponse;
import org.springframework.stereotype.Service;

/**
 * Import 를 이용한 결제 Service
 * @author shl
 * @since 2024-07-14
 */
@Service
public class ImportPaymentServiceImpl implements Payment {

    /**
     * 결제
     * @param payment
     * @return 결제 성공 여부
     */
    @Override
    public PaymentResponse pay(PaymentRequest payment) {
        // 1. 결제 전 access token 존재 여부 및 만료 여부 확인
        // 2-1) 만료 됬으면 다시 발급 메소드 호출
        // 2-2) 만료 안됬으면 바로 결제 프로세스
        // 3. 결제 프로세스 진행
        // 4-1) 실패하면 에러 처리 -> 어떻게 해야할지?? 다시 시도하는건지? 등등 -> 실패 로그 및 결과 저장
        // 4-2) 성공하면 성공 로그 및 결과 저장
        // 5. 해당 내용 반환

        return null;
    }
}
