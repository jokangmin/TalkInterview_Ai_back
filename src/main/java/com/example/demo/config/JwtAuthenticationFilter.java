package com.example.demo.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.demo.util.JwtUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil; // JwtTokenProvider 대신 JwtUtil 주입

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        // resolveToken 메서드는 JwtUtil에 없으므로 직접 구현 또는 수정 필요
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        System.out.println("🔑 추출된 토큰: " + token);

        if (token != null && jwtUtil.validateToken(token)) { // jwtUtil 사용
            String userId = jwtUtil.getUserIdFromToken(token); // jwtUtil 사용
            System.out.println("✅ 유효한 토큰, 사용자 ID: " + userId);

            UserEntity user = userRepository.findByUserId(userId);
            System.out.println("🙋 사용자 조회 결과: " + user);

            if (user != null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } else {
            System.out.println("❌ 유효하지 않은 토큰");
        }

        chain.doFilter(request, response);
    }
}