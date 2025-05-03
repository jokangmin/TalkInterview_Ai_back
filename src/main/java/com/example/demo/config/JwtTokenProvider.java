package com.example.demo.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

    // application.properties 또는 yml에 설정
    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효시간 (예: 1시간)
    private long tokenValidTime = 60 * 60 * 1000L;

    // ✅ 토큰 생성
    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId); // sub에 userId 저장

        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .setClaims(claims) // payload
                .setIssuedAt(now) // iat
                .setExpiration(expiration) // exp
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명
                .compact();
    }

    // ✅ 토큰에서 userId 추출
    public String getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ HTTP 요청 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
}
