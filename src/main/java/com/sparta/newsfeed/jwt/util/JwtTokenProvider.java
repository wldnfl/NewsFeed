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

    public static final String HEADER_STRING = "Authorization";
    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;
    private final UserRepository userRepository;

    public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey,
                            @Value("${jwt.access.expiration}") long accessExpiration,
                            @Value("${jwt.refresh.expiration}") long refreshExpiration, UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.userRepository = userRepository;
    }

    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, accessExpiration);
    }

    public String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId, refreshExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            String userId = extractUsername(token);
            System.out.println("유효성 검사할 토큰의 사용자 ID: " + userId);
            return !isTokenExpired(token);
        } catch (JwtException e) {
            System.out.println("토큰 유효성 검사 실패: " + e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }


    //토큰 유져 뽑기
    public User getTokenUser(HttpServletRequest httpServletRequest) {
        String userId = extractUsername(getStringtoken_2(httpServletRequest));
        return userRepository.findByUserId(userId);
    }

    // HttpServletRequest에서 토큰 뽑아오기
    public String getStringtoken_1(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(HEADER_STRING)) {
                token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
            }

        }}
        return token;
    }

    // 토큰 머리 꼬리 자르기
    public  String getStringtoken_2(HttpServletRequest request) {
        String token = getStringtoken_1(request);
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    // 토큰 가져오기
    public void addToken(String token, HttpServletResponse response) {
        token = "Bearer "+token;
        token = URLEncoder.encode(token, StandardCharsets.UTF_8 ).replaceAll("\\+", "%20");

        Cookie cookie = new Cookie(HEADER_STRING, token);
        cookie.setPath( "/" );

        response.addCookie( cookie );

    }
}
