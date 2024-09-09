package com.bowmeow.payment.service;

import com.bowmeow.payment.PaymentServiceGrpc;
import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.product.ProductServiceProto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Import 를 이용한 결제 Service
 * @author shl
 * @since 2024-07-14
 */
@Service
@RequiredArgsConstructor
public class ImportPaymentGrpcServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    private ProductClient productClient;

    /**
     * Product 서비스에서 상품 정보 가져오기
     * @param productId 가져올 상품 일련번호
     * @return {@link com.bowmeow.product.ProductServiceProto.ProductInfo}
     */
    public ProductServiceProto.ProductInfo getProductInfo(Integer productId) {
        return productClient.getProductInfo(productId);
    }
}
