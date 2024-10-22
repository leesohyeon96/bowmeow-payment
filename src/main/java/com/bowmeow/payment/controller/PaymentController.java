package com.bowmeow.payment.controller;

import com.bowmeow.payment.domain.PaymentRequest;
import com.bowmeow.payment.domain.PaymentUpdateRequest;
import com.bowmeow.payment.dto.PaymentUpdateRequestDTO;
import com.bowmeow.payment.dto.ProductInfoDTO;
import com.bowmeow.payment.service.PaymentService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final PaymentService paymentService;

    // todo: 트랜젝션 처리 필요

    /**
     * 주문서 생성
     * - [결제하기] 버튼 누른 후 주문서 생성 및 화면 이동
     * @return wait
     */
    @PostMapping("/orders")
    public String getOrder(@RequestHeader("Authorization") String authorizationHeader, Model model, @RequestBody ProductInfoDTO request) {
        log.debug("GET /payments/orders invoke");
        // 사용자 아이디 JWT 토큰 헤더에서 꺼내기
        // 1. 주문서 생성 -> 실패해도 20분 지나면 만료상태 되도록 할 거니까 따로 보는거?
        // 1. 주문서 만들기
        // - 주문 id, 주문 들어온 시간 , 현재상태(결제대기, 결제완료, 결제취소(=환불), 주문 만료), 사용자 아이디(userId),
        // 해당 사람은 무조건 1개의 주문서만 가질 수 있음 (상품 당 주문서 다 가질 수 없음!)
        String token = authorizationHeader.replace("Bearer ", "");
        Long orderId = paymentService.saveOrder(token);

        log.debug("saveOrder method success");
        model.addAttribute("orderId", orderId);
        model.addAttribute("productInfo", request);
        return "orders";
    }

    /**
     * 결제할 product 정보 조회
     * - product 정보의 일관성을 지키기 위함
     */
    @PostMapping("/products/{productId}")
    public Map<String, Object> payment(@PathVariable Integer productId)  {
        // 클라이언트에 결제 정보 반환
        return paymentService.payment(productId);
    }

    /**
     * 결제
     * @return 결제 결과
     */
    @PostMapping("/")
    public IamportResponse<Payment> payment(@RequestBody PaymentRequest request) {
        // postman 으로 테스트 시 iamport 에서 제공하는 JSON 테스트 데이터 사용하면 됨
        // ex) {
        //  "merchant_uid": "unique_merchant_id",
        //  "amount": 100.0,
        //  "card_number": "1234-5678-9012-3456",
        //  "expiry": "2024-12",
        //  "birth": "900101",
        //  "pwd_2digit": "12"
        //}
        return paymentService.payment(request);
    }


    /**
     * 실제 결제 여부 확인 및 결제 정보 업데이트
     * @param request {@link PaymentUpdateRequestDTO}
     * @return 확인
     */
    @PutMapping("/{orderId}")
    public Map<String, Object> updatePaymentInfo(@PathVariable Integer paymentId,
                                                 @RequestBody PaymentUpdateRequestDTO request) {
        PaymentUpdateRequest paymentUpdate = modelMapper.map(request, PaymentUpdateRequest.class);
        paymentUpdate.setPaymentId(paymentId);

        Integer result = paymentService.paymentVerificationAndUpdate(paymentUpdate);
        boolean isSuccess = result == 1;

        Map<String, Object> response = new HashMap<>();
        response.put("success", isSuccess);
        return response;
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
}
