package com.bowmeow.payment.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 request domain
 */
@Getter
@Setter
public class PaymentRequest {
    /** payment access token */
    private String accessToken;
    /** user info */
//    private UserInfo userInfo; // userSno, userTimeZone 등
    /** request date (DB에는 utc로 저장될 것) */
    private LocalDateTime requestDate;
    /** product info */
//    private ProductInfo productInfo; //
    /** expired date */
    private LocalDateTime expiredDate; // imp : expired_at, paypal : expires_in
    /** key for access token */
    private String apiKey; // paypal : clientKey
    /** secret for access token */
    private String apiSecret; // paypal : clientSecret

    // iamport 에 필요한 request
    /** 상점 주문 번호(고유 주문 번호) */
    @NotBlank(message = "상점 주문 번호 없음")
    private String merchant_uid;
    /** 결제 금액 */
    @NonNull
    private BigDecimal amount;
    /** 카드 번호 */
    @NotBlank(message = "카드 번호 없음")
    private String card_number;
    /** 카드 만료일 */
    @NotBlank(message = "카드 만료일 없음")
    private String expiry;
    /** 생년월일 */
    @NonNull
    private int birth;
    /** 카드 비밀번호 앞 두 자리 */
    @NonNull
    private int pwd_2digit;


    // 결제 토근 request domain
        // 1. accessToken, userInfo(유저정보-> 여기 userSno랑, timeZone도 있고할둣), utcDate, productInfo(객체예정), expired_at(이거 저장해놓긴 해야함)
        // API key, API secret(얘네는 서버상에 저장해둠, profiles 사용해서 다 따로따로??)
    // 결제 토근 response domain
        // 1. userInfo, 성공여부, 결제 상태(결제대기, 결제승인, 환불대기, 환불승인)
        // -> 이거 한다음에 DB에 저장해야 함(JPA로 할까?, queryDSL도 써보장ㅇㅇ)
    // 결제용 request domain (취소용 같이 쓸수있으려나?)
    // 결제용 response domain >> 결제정보 저장용 쓰고, 그 결제이력 조회용 request, response 로 같이 쓰면 될듯?
    // 결제 취소용 request domain
    // 결제 취소용 response domain
    // 결제 이력 조회용 request domain
    // 결제 이력 조회용 response domain

    // 겹치는 걸 abstract class 의 필드로 만들어서 extends 받거나
    // 구성(composition)할 수도 있음 ㅇㅇ >> private PaymentBase paymentBase; 이렇게
    //    // ---> 1) import : API key, API secret 받아서 POST /users/getToken 호출 >> access token 받음
    //    //      그걸 JWT 방식으로 다른 거 request 요청할때 header 에 넣어주면 됨!
    //    // ---> 2) paypal : client ID, client secret 받아서 /v1/oauth2/token 호출 >> access token 이랑 유효한 시간(expires_in) 받음 ㅇㅇ
    //    //      그걸 JWT 방식으로 다른 거 request 요청할때 header 에 넣어주면 됨!
    //    // 그럼 어떻게 interface 형태로 만들까??
    //    // - 결제interface에 메소드 -> 결제하기, 결제 expire 된거 다시 호출하기??(아니면 그냥 결제하기 구현체에서 해주면 되지 않나?)
}
