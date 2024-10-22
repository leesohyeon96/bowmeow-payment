package com.bowmeow.payment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity(name = "orders")
@Setter
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략: 데이터베이스에 의존하여 자동 증가
    private Long orderId;

    @Column(name = "USER_ID", nullable = false) // 사용자 ID
    private String userId;

    @Column(name = "ORDER_INSERT_TIME", nullable = false, updatable = false) // 주문 생성 시간
    private LocalDateTime orderInsertTime;

    @Column(name = "ORDER_STATUS", nullable = false) // 주문 상태
    private String orderStatus;

    @Column(name = "PRODUCT_PURCHASE_COUNT", nullable = false) // 주문 상품 갯수
    private Integer productPurchaseCount;

    // 엔티티 생성 시 orderInsertTime를 현재 UTC 시간으로 고정
    @PrePersist
    public void prePersist() {
        this.orderInsertTime = LocalDateTime.now(ZoneOffset.UTC);
    }
}
