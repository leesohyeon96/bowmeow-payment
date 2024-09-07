package com.bowmeow.payment.service;

import com.bowmeow.payment.entity.Order;
import com.bowmeow.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentJPAService {

    private PaymentRepository paymentReposiotry;

    public Long saveOrder(String userId) {
        Order order = new Order();
        order.setUserId(userId);
        Order saveOrder = paymentReposiotry.saveOrder(order);
        return saveOrder.getOrderId();
    }
}
