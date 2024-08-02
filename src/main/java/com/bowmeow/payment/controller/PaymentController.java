package com.bowmeow.payment.controller;

import com.bowmeow.payment.client.ProductClient;
import com.bowmeow.payment.dto.PaymentRequestDTO;
import com.bowmeow.payment.dto.ProductRequestDTO;
import com.bowmeow.payment.service.ImportPaymentServiceImpl;
import com.bowmeow.product.ProductServiceProto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Payment Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Slf4j
public class PaymentController {

    private final ModelMapper modelMapper;
    private final ImportPaymentServiceImpl importPaymentService;
    private final ProductClient productClient;

    /**
     * 결제를 위한 정보 반환
     */
    @PostMapping("")
    public Map<String, Object> getProductInfo(@RequestBody ProductRequestDTO request)  {
        // 1. Product 서비스에서 상품 정보 가져오기 (예: gRPC 호출)
        ProductServiceProto.ProductInfo productInfo = productClient.getProductInfo(request.productSno());
        // 내일할일 TODO: product 프로젝트 만들고 거기서 .proto, client 파일 만들어서 gRPC 요청 받아서 데이터 받아오기 ~

        // 2. 결제 정보 구성
        Map<String, Object> productData = new HashMap<>();
//        productData.put("merchant_uid", generateUniquePaymentId());
        productData.put("productSno", productInfo.getProductSno());
        productData.put("productPrice", productInfo.getProductPrice());
        productData.put("productName", productInfo.getProductName());

        // 3. 클라이언트에 결제 정보 반환
        return productData;
    }





    // DB는 뭐사용할까?
    // # 결제 이력이 필요하긴 하니까 RDBMS -> PostgreSQL
    // todo: postgreSQL 연동하는데 -> MSA 구조에서 dockerfile 로 만들어서 ??


    /**
     * 결제
     */
//    @PostMapping("")
    public String payment(PaymentRequestDTO paymentRequest) {
        // todo:  modelMapper > 내부 이름 수정해서 설정하도록 해야함
        log.debug("payment project - payment method invoke!");
//        PaymentRequest pRequest = modelMapper.map(paymentRequest, PaymentRequest.class);
//        importPaymentService.payment(pRequest);
//        importPaymentService.payment();
        log.debug("payment success!");
        return "OK";
    }

    // todo: 사람들은 어떤 라이브러리 써서 결제 하는지 찾아보기
    // 1) Stripe : 글로벌 서비스, 다양한 국가/통화 지원
    // 2) Import : 한국에서 가장 많이 씀, 여러 결제 수단 통합해서 제공 : https://developers.portone.io/api/rest-v1?v=v1
    // 3) PayPal : 전세계에서 많이 사용됨, 강력한 보안 제공 : https://developer.paypal.com/api/rest/
    // 4. 토스페이먼츠 : 한국에서 많이 사용됨, https://www.tosspayments.com/blog/articles/tosspayments-api-1
    // -> 일단은 import 로 하고, 그다음에 PayPal 도 해보자
    // - 그럼 interface 형태로 만드는게 나을듯 >> 나중에 갈아끼우기 위해서

    // ---> 1) import : API key, API secret 받아서 POST /users/getToken 호출 >> access token 받음
    //      그걸 JWT 방식으로 다른 거 request 요청할때 header 에 넣어주면 됨!
    // ---> 2) paypal : client ID, client secret 받아서 /v1/oauth2/token 호출 >> access token 이랑 유효한 시간(expires_in) 받음 ㅇㅇ
    //      그걸 JWT 방식으로 다른 거 request 요청할때 header 에 넣어주면 됨!
    // 그럼 어떻게 interface 형태로 만들까??
    // - 결제interface에 메소드 -> 결제하기, 결제 expire 된거 다시 호출하기??(아니면 그냥 결제하기 구현체에서 해주면 되지 않나?)


    // 메소드를 먼저 생각해고
    // 그게에 맞는 결제 프로세스 생각하셈 ㅇㅇ (834 API 참고) -> 아임포트 token 생성함(결제를 하면 토큰을 주는거같기도 ㅇㅇ)
    // 1. 결제 method
        // 결제 성공/실패 여부
    // 2. 결제취소 (환불)
        // 결제취소 성공/실패 여부
    // 3. 결제 이력 조회

    // request
        // 1. 결제 method
            // - member 일련번호
            // - 상품 정보 (일련번호, 가격, 통화, 등등??)
    // response
        // 1. 결제 method
            // - 성공 여부
            // - 뭐가 필ㅇ하지?

}
