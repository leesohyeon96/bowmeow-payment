package com.bowmeow.payment.service;

import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.payment.domain.ProductInfo;
//import com.bowmeow.product.ProductServiceProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private ProductClient productClient;
    private ImportPaymentGrpcServiceImpl importPaymentGrpcService;
    private PaymentJPAService paymentJPAService;
    private JWTService jwtService;

    /**
     * 결제
     * - 결제할 product 정보 조회 > product 정보의 일관성을 지키기 위함
     * - 결제
     */
    public Map<String, Object> payment(ProductInfo productInfo) {

        // 1. productId 이용하여 Product 서비스에서 상품 정보 가져오기 (예: gRPC 호출)
//        ProductServiceProto.ProductInfo product = importPaymentGrpcService.getProductInfo(productInfo.getProductId());

        // 2. 결제 정보 구성
        Map<String, Object> productData = new HashMap<>();
//        productData.put("merchant_uid", generateUniquePaymentId()); // TODO : merchant_uid 할지말지
//        productData.put("productSno", product.getProductSno());
//        productData.put("productPrice", product.getProductPrice());
//        productData.put("productName", product.getProductName());

        // 3. 결제
//        importPaymentService.payment(); // 결제 연동하기


        return productData;
    }

    /**
     * 주문서 생성
     * - userId를 가져옴
     * - 주문서 생성
     * @return 주문아이디
     */
    public Long saveOrder(String token) {
        // 1. userId 가져옴
        String userId = jwtService.getExtractUserIdFromToken(token);
        log.info("userId for saveOrder = {}", userId);
        // 2. 주문서 생성
        // - 임시로 1개의 row 생성
        //   주문 id, 주문 들어온 시간 , 현재상태(결제대기, 결제완료, 결제취소(=환불), 주문 만료), 사용자 아이디(userId),
        // - 후에 주문서 작성 후 '실제 결제' 버튼 누르는 경우
        return paymentJPAService.saveOrder(userId);
    }
}
