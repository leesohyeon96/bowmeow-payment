package com.bowmeow.payment.service;

import com.bowmeow.payment.domain.PaymentUpdateRequest;
import com.bowmeow.payment.entity.Order;
import com.bowmeow.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentJPAService {

    private PaymentRepository paymentRepository;

    public Long saveOrder(String userId) {
        Order order = new Order();
        order.setUserId(userId);
        Order saveOrder = paymentRepository.saveOrder(order);
        return saveOrder.getOrderId();
    }

    /**
     * 주문서 생성
     * @return {@link Order}
     */
    public Order saveOrder(Order order) {
        return paymentRepository.saveOrder(order);
    }

    /**
     * 결제 정보 업데이트
     * @param paymentUpdate {@link PaymentUpdateRequest}
     * @return 결제 정보 업데이트 여부
     */
    public Integer savePayment(PaymentUpdateRequest paymentUpdate) {
        // 만든 Repository 넣음 -> Order이
//        paymentRepository.save(paymentUpdate);
        return null;
    }
}
