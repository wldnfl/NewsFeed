package com.sparta.newsfeed.jwt.filter;

import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 필터 작동시 로그 추가.
        logger.info("JwtAuthenticationFilter 필터 작동!!");
        logger.info("입력받은 URI : " + request.getRequestURI());
        // 헤더에서 토큰 가져오기
        String authorizationHeader = jwtTokenProvider.getStringtoken_1(request);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // 토큰 유효성 검사
                if (jwtTokenProvider.validateToken(token)) {
                    // 토큰에서 사용자 ID 추출하여 인증 객체 생성 후 SecurityContext에 설정
                    String userId = jwtTokenProvider.extractUsername(token);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                // 유효하지 않은 토큰일 경우 예외 처리 후 응답
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
                return;
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
