package com.sparta.newsfeed.controller;


import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.dto.emaildto.EmailRequestDto;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.service.SignUpService;
import com.sparta.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private UserService userService;
    private final SignUpService signUpService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/user/sign")
    public String addUser(@Valid @RequestBody SignUpRequestDto requestDto) {
        return  signUpService.addUser(requestDto);
    }

    // 이메일 인증
    @PostMapping("/user/verify")
    public String verifyEmail(@Valid @RequestBody EmailRequestDto requestDto) {
        signUpService.verifyEmail(requestDto);
        return "이메일 인증 성공";
    }

    // 로그인
    @PostMapping("/user/login")
    public String loginUser(@Valid @RequestBody SignUpRequestDto requestDto, HttpServletResponse response) {
        return signUpService.loginUser(requestDto, response);
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logoutUser(/*@RequestHeader("AccessToken") String accessToken,*/ HttpServletResponse response) {
        return signUpService.logoutUser(response);
    }

    // 회원 탈퇴
    @PostMapping("/user/delete")
    public String deleteUser(/*@RequestHeader("AccessToken") String accessToken,*/
            UserRequestDto userRequestDto, HttpServletResponse response) {
        return signUpService.deleteUser(userRequestDto,response);
    }

    @GetMapping("user/profile/{userId}")
    public UserResponseDto getUserProfile(@PathVariable Long userId) throws Exception {
        return userService.getUserProfile(userId);
    }

    @PatchMapping("user/profile/{userId}")
    public UserResponseDto updateUserProfile(@PathVariable Long userId,
                                          @RequestBody UserRequestDto userRequestDto,
                                          Principal principal) throws Exception {
        return userService.updateUserProfile(userId, userRequestDto);
    }


}