package com.bowmeow.payment.service;

import com.bowmeow.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentJPAService {

    private PaymentRepository paymentReposiotry;

    public void saveOrder(String userId) {
        // TODO: 객체 만들어서 밑에 필요한 인자 set 한 뒤 saveOrder 마무리 하기
        // 주문 id, 주문 들어온 시간 , 현재상태(결제대기, 결제완료, 결제취소(=환불), 주문 만료), 사용자 아이디(userId),
    }
}
