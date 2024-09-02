package com.bowmeow.payment.service;

import com.bowmeow.payment.PaymentServiceGrpc;
import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.payment.domain.PaymentRequest;
import com.bowmeow.payment.domain.PaymentResponse;
import com.bowmeow.payment.property.ImportProperties;
import com.bowmeow.product.ProductServiceProto;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Import 를 이용한 결제 Service
 * @author shl
 * @since 2024-07-14
 */
@Service
@NoArgsConstructor
public class ImportPaymentGrpcServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {
    private ProductClient productClient;
    private ImportProperties importProperties;

    @Autowired
    public ImportPaymentGrpcServiceImpl(ImportProperties importProperties) {
        this.importProperties = importProperties;
    }


//    @Override
//    public void payment(PaymentRequest request) {
//
//    }

//    @Override
//    public void payment() {
//
//    }

//    /**
//     * 결제
//     * @return 결제 성공 여부
//     */
//    @Override
//    public void payment(/* PaymentRequest request */) {
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
//    }

//    private void getAccessToken() {
//        // todo: +) profiles 설정도 추가로 해줄지 고민중
//        String key = importProperties.getKey();
//        String secret = importProperties.getSecret();
//
//        // /users/getToken API를 호출
//        // restClient 나 webClient 사용예정(http/웹/rest 클라이언트) 웹통신
//    }
//
//    @Override
//    public void payment(Empty request, StreamObserver<Empty> responseObserver) {
//        super.payment(request, responseObserver);
//    }

    /**
     * Product 서비스에서 상품 정보 가져오기
     * @param productId 가져올 상품 일련번호
     * @return {@link com.bowmeow.product.ProductServiceProto.ProductInfo}
     */
    public ProductServiceProto.ProductInfo getProductInfo(Integer productId) {
        return productClient.getProductInfo(productId);
    }

//    @Override
//    public PaymentResponse pay(PaymentRequest payment) {
//        return null;
//    }
}
