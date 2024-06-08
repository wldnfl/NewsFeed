package com.sparta.newsfeed.controller;


import com.sparta.newsfeed.dto.UserDto.LoginUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.dto.emaildto.EmailRequestDto;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.service.SignUpService;
import com.sparta.newsfeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final SignUpService signUpService;

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
    public String loginUser(@Valid @RequestBody LoginUpRequestDto requestDto, HttpServletResponse response) {
        return signUpService.loginUser(requestDto, response);
    }

    // 로그아웃
    @PostMapping("/user/logout")
    public String logoutUser(/*@RequestHeader("AccessToken") String accessToken,*/ HttpServletRequest request,HttpServletResponse response) {
        return signUpService.logoutUser(request,response);
    }

    // 회원 탈퇴
    @PostMapping("/user/delete")
    public String deleteUser(/*@RequestHeader("AccessToken") String accessToken,*/
            @RequestBody
            LoginUpRequestDto loginUpRequestDto,
            HttpServletRequest request,
            HttpServletResponse response) {
        return signUpService.deleteUser(loginUpRequestDto,request,response);
    }

    // 유저 프로필 가져오기
    @GetMapping("/user/profile")
    public UserResponseDto getUserProfile(HttpServletRequest request) {
        return userService.getUserProfile(request);
    }

    // 유저 프로필 수정
    @PatchMapping("/user/profile")
    public UserResponseDto updateUserProfile(HttpServletRequest request, @RequestBody UserRequestDto userRequestDto){
        return userService.updateUserProfile(request,userRequestDto);
    }

}