package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.service.SignUpService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/user/signup")
    public String addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        signUpService.addUser(requestDto);
        return "회원가입 성공";
    }

    // 로그인
    @PostMapping("/user/login")
    public String loginUser(@Valid @RequestBody SignUpRequestDto requestDto, HttpServletResponse response) {
        Map<String, String> tokens = signUpService.loginUser(requestDto, response);
        return "로그인 성공";
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logoutUser(/*@RequestHeader("AccessToken") String accessToken,*/ HttpServletResponse response) {
//        signUpService.logoutUser(accessToken.replace("Bearer ", ""));
        jwtTokenProvider.deleteCookie(response);
        return "로그아웃 성공";
    }

    // 회원 탈퇴
    @PostMapping("/user/delete")
    public String deleteUser(/*@RequestHeader("AccessToken") String accessToken,*/ @Valid @RequestBody SignUpRequestDto requestDto) {
//        String userId = jwtTokenProvider.extractUsername(accessToken.replace("Bearer ", ""));
//        signUpService.deleteUser(userId, requestDto.getPassword());
        return "회원탈퇴 성공";
    }
}
