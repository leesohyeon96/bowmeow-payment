package com.bowmeow.payment.service;

import com.bowmeow.common.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private final JWTUtils jwtUtils;

    public JWTService() {
        this.jwtUtils = new JWTUtils();
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
