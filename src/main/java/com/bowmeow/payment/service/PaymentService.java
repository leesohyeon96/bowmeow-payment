package com.bowmeow.payment.service;

import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.payment.domain.ProductInfo;
//import com.bowmeow.product.ProductServiceProto;
import com.bowmeow.payment.property.ImportProperties;
import com.bowmeow.product.ProductServiceProto;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private ProductClient productClient;
    private ImportPaymentGrpcServiceImpl importPaymentGrpcService;
    private PaymentJPAService paymentJPAService;
    private JWTService jwtService;
    private ImportProperties importProperties;

    /**
     * 결제할 product 정보 조회
     * - product 정보의 일관성을 지키기 위함
     */
    public Map<String, Object> payment(ProductInfo productInfo) {
         // 1. productId 이용하여 Product 서비스에서 상품 정보 가져오기 (예: gRPC 호출)
        ProductServiceProto.ProductInfo product = importPaymentGrpcService.getProductInfo(productInfo.getProductId());

        // 2. 결제 정보 구성
        Map<String, Object> productData = new HashMap<>();
//        productData.put("merchant_uid", generateUniquePaymentId()); // TODO : merchant_uid 할지말지
        productData.put("productSno", product.getProductSno());
        productData.put("productPrice", product.getProductPrice());
        productData.put("productName", product.getProductName());

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

    /**
     * 결제 -> 그 실제 결제하는 거에서 사용하기!!
     */
    public void payment(/* PaymentRequest request */) {
//        Rectangle request =
//                Rectangle.newBuilder()
//                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
//                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
//        Iterator<Feature> features;
//        try {
//            features = blockingStub.listFeatures(request);
//        } catch (StatusRuntimeException e) {
//            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
//            return;
//        }

        // 1. 결제 전 access token 존재 여부 및 만료 여부 확인
        // 2-1) 만료 됬으면 다시 발급 메소드 호출
        // 2-2) 만료 안됬으면 바로 결제 프로세스
        // 3. 결제 프로세스 진행
        // 4-1) 실패하면 에러 처리 -> 어떻게 해야할지?? 다시 시도하는건지? 등등 -> 실패 로그 및 결과 저장
        // 4-2) 성공하면 성공 로그 및 결과 저장
        // 5. 해당 내용 반환

        // # accessToken 존재여부 만료여부확인
//        String accessToken = request.getAccessToken();
//        if (accessToken.isEmpty() && accessToken.isBlank()) {
//            getAccessToken();
//        }
    }

    private void getAccessToken() {
        // todo: +) profiles 설정도 추가로 해줄지 고민중
        String key = importProperties.getKey();
        String secret = importProperties.getSecret();

        // /users/getToken API를 호출
        // restClient 나 webClient 사용예정(http/웹/rest 클라이언트) 웹통신
    }
}
