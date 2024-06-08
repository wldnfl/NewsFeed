package com.sparta.newsfeed.jwt.util;

import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    // 헤더 문자열 상수
    public static final String HEADER_STRING = "Authorization";

    private final SecretKey secretKey; // JWT 서명을 위한 비밀 키
    private final long accessExpiration; // 액세스 토큰 만료 시간 (밀리초)
    private final long refreshExpiration; // 리프레시 토큰 만료 시간 (밀리초)
    private final UserRepository userRepository; // 사용자 저장소

    // 생성자
    public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey,
                            @Value("${jwt.access.expiration}") long accessExpiration, UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = 14 * 24 * 60 * 60 * 1000L; // 2주로 설정
        this.userRepository = userRepository;
    }

    // JWT 생성
    public String generateToken(String userId) {
        // 클레임 설정
        Map<String, Object> claims = new HashMap<>();
        // 토큰 생성 및 반환
        return createToken(claims, userId, accessExpiration);
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String userId) {
        // 클레임 설정
        Map<String, Object> claims = new HashMap<>();
        // 리프레시 토큰 생성 및 반환
        return createToken(claims, userId, refreshExpiration);
    }

    // 토큰 생성
    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims) // 클레임 설정
                .setSubject(subject) // 주제 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간 설정
                .signWith(secretKey) // 서명 설정
                .compact(); // 토큰 생성
    }

    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰에서 모든 클레임 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            // 토큰에서 사용자 ID 추출
            String userId = extractUsername(token);
            System.out.println("유효성 검사할 토큰의 사용자 ID: " + userId);
            // 토큰의 만료 여부 반환
            return !isTokenExpired(token);
        } catch (JwtException e) {
            // 유효성 검사 실패 시 예외 처리
            System.out.println("토큰 유효성 검사 실패: " + e.getMessage());
            return false;
        }
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // HttpServletRequest에서 토큰을 가져와 사용자 반환
    public User getTokenUser(HttpServletRequest httpServletRequest) {
        // 헤더에서 토큰 추출 후 사용자 반환
        String userId = extractUsername(getStringtoken_2(httpServletRequest));
        return userRepository.findByUserId(userId);
    }

    // HttpServletRequest에서 토큰 문자열 가져오기
    public String getStringtoken_1(HttpServletRequest request) {
        // 쿠키에서 토큰 가져오기
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HEADER_STRING)) {
                    token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                }
            }
        }
        return token;
    }

    // 토큰 문자열에서 "Bearer " 제거
    public String getStringtoken_2(HttpServletRequest request) {
        // 토큰 문자열 가져오기
        String token = getStringtoken_1(request);
        // "Bearer " 제거 후 반환
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    // HttpServletResponse에 토큰 쿠키 추가
    public void addToken(String token, HttpServletResponse response) {
        // "Bearer " 접두어 추가 및 URL 인코딩
        token = "Bearer " + token;
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        // 토큰을 쿠키에 추가하고 응답에 설정
        Cookie cookie = new Cookie(HEADER_STRING, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
