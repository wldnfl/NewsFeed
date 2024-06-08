package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.UserDto.LoginRequestDto;
import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.message.MessageResponseDto;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.service.SignUpService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/user/signup")
    public MessageResponseDto addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        signUpService.addUser(requestDto);
        return new MessageResponseDto("회원가입 성공");
    }

    // 로그인
    @PostMapping("/user/login")
    public MessageResponseDto loginUser(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        Map<String, String> tokens = signUpService.loginUser(requestDto, response);
        createAccessTokenCookie(response, tokens.get("accessToken"));
        return new MessageResponseDto("로그인 성공");
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public MessageResponseDto logoutUser(@RequestHeader("Authorization") String accessToken, HttpServletResponse response) {
        signUpService.logoutUser(accessToken.replace("Bearer ", ""));
        deleteAccessTokenCookie(response);
        return new MessageResponseDto("로그아웃 성공");
    }

    // 회원 탈퇴
    @PostMapping("/user/delete")
    public MessageResponseDto deleteUser(@RequestHeader("Authorization") String accessToken, @Valid @RequestBody SignUpRequestDto requestDto) {
        String userId = jwtTokenProvider.extractUsername(accessToken.replace("Bearer ", ""));
        signUpService.deleteUser(userId, requestDto.getPassword());
        return new MessageResponseDto("회원탈퇴 성공");
    }

    // 쿠키 생성 및 설정
    private void createAccessTokenCookie(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1800); // 30분
        response.addCookie(accessTokenCookie);
    }

    // 쿠키 삭제
    private void deleteAccessTokenCookie(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0); // 쿠키 삭제
        response.addCookie(accessTokenCookie);
    }
}
