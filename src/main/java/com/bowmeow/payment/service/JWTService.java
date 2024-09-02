package com.bowmeow.payment.service;

import com.bowmeow.common.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private final JWTUtils jwtUtils;

    public JWTService(@Value("${jwt.secretKey}")String secretKey
                    , @Value("${jwt.expirationTime}")long expirationTime) {
        // TODO: 이렇게 사용할 프로젝트들에서 secretKey, expirationTime을 각 Application.yml 설정에서 가져올거면 뭐하러 common을 구현해주었나??
        // 고민하기
        this.jwtUtils = new JWTUtils(secretKey, expirationTime);
    }

    public String getExtractUserIdFromToken(String token) {
        if (jwtUtils.isTokenValid(token)) {
            return jwtUtils.extractUserId(token);
        } else {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }

    // 추가적인 메서드 예시
    public boolean validateToken(String token) {
        return jwtUtils.isTokenValid(token);
    }
}
