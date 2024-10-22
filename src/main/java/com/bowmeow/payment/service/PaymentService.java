package com.bowmeow.payment.service;

import com.bowmeow.payment.domain.PaymentRequest;
import com.bowmeow.payment.domain.PaymentUpdateRequest;
import com.bowmeow.payment.exception.PaymentFailedException;
import com.bowmeow.payment.property.ImportProperties;
import com.bowmeow.product.ProductServiceProto;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CardInfo;
import com.siot.IamportRestClient.request.OnetimePaymentData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final ImportPaymentGrpcServiceImpl importPaymentGrpcService;
    private final PaymentJPAService paymentJPAService;
    private final JWTService jwtService;
    private final ImportProperties importProperties;
    private final IamportClient iamportClient;
    private final ModelMapper modelMapper;

    /**
     * 결제할 product 정보 조회
     * - product 정보의 일관성을 지키기 위함
     */
    public Map<String, Object> payment(Integer productId) {
         // 1. productId 이용하여 Product 서비스에서 상품 정보 가져오기 (예: gRPC 호출)
        ProductServiceProto.ProductInfo product = importPaymentGrpcService.getProductInfo(productId);

        // 2. 결제 정보 구성
        Map<String, Object> productData = new HashMap<>();
        productData.put("merchant_uid", generateUniquePaymentId());
        productData.put("productSno", product.getProductSno());
        productData.put("productPrice", product.getProductPrice());
        productData.put("productName", product.getProductName());

        return productData;
    }

    /**
     * UUID 생성
     * @return 생성된 UUID
     */
    private String generateUniquePaymentId() {
        return "product_" + UUID.randomUUID();
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
     * 결제
     */
    public IamportResponse<Payment> payment(PaymentRequest request) {
        CardInfo cardInfo = modelMapper.map(request, CardInfo.class);
        OnetimePaymentData paymentData = new OnetimePaymentData(request.getMerchant_uid(), request.getAmount(), cardInfo);

        IamportResponse<Payment> paymentResponse;
        try {
            paymentResponse = iamportClient.onetimePayment(paymentData);
            if (paymentResponse.getResponse() == null) {
                throw new PaymentFailedException("iamport 결제 실패");
            }
        } catch (IamportResponseException | IOException e) {
            log.error("결제 요청 실패 :", e);
            throw new RuntimeException(e);
        }
        log.info("payment succeed={}", paymentResponse);
        return paymentResponse;
    }

    /**
     * 결제 정보 저장
     * @param paymentUpdate {@link PaymentUpdateRequest}
     * @return 확인
     */
    @Transactional(rollbackOn = Exception.class)
    public Integer paymentVerificationAndUpdate(PaymentUpdateRequest paymentUpdate) {
//        Payment

        paymentJPAService.savePayment(paymentUpdate);
        // 1. 결제 정보 저장
        return null;

    }
}
