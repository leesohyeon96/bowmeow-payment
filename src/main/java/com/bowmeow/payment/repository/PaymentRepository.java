package com.bowmeow.payment.repository;

import com.bowmeow.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Order, Long> {
    Order saveOrder(Order order);
}
