package com.sparta.newsfeed.service;



import com.sparta.newsfeed.dto.UserDto.LoginRequestDto;
import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.entity.EmailVerification;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.entity.User_entity.UserStatus;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.EmailVerificationRepository;
import com.sparta.newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Getter
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private SignUpRequestDto requestDto;

    // 유저 회원가입
    public String addUser(SignUpRequestDto requestDto) {
        this.requestDto = requestDto;
        User existingUser = userRepository.findByUserId(requestDto.getUserId());
        if (existingUser != null) {
            if (existingUser.getUserStatus() == UserStatus.ACTIVE) {
                throw new IllegalArgumentException("중복된 사용자 ID입니다.");
            } else if (existingUser.getUserStatus() == UserStatus.WITHDRAWAL) {
                throw new IllegalArgumentException("탈퇴한 사용자 ID입니다.");
            }
        }

        User user = new User(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setUserStatus(UserStatus.WAIT_EMAIL); // 이메일 인증을 하기 전에는 이메일 인증을 기다리는 상태코드.

        // 인증코드 생성하여 이메일 전송
        String code = generateVerificationCode();
        EmailVerification emailVerification = new EmailVerification(requestDto.getEmail(), code);
        emailVerificationRepository.save(emailVerification);
        sendVerificationEmail(requestDto.getEmail(), code);

        return userRepository.save(user);
        userRepository.save(user);
        return requestDto.getUsername() + "님 회원가입을 축하합니다";
    }

    // 이메일 인증시 보낼 인증 코드
    private String generateVerificationCode() {
        // 랜덤 돌리기
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    // 이메일
    private void sendVerificationEmail(String email, String code) {
        String subject = "이메일 인증 코드";
        String text = "인증 코드 번호 : " + code;
        try {
            emailService.sendEmail(email, subject, text);
        } catch (Exception e) {
            throw new RuntimeException("이메일 인증에 실패했습니다.", e);
        }
    }

    // 유저 로그인
    public String loginUser(SignUpRequestDto requestDto , HttpServletResponse response) {
        User user = userRepository.findByUserId(requestDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("유저 아이디가 올바르지 않습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("유저 비밀번호가 올바르지 않습니다.");
        }

        if (user.getUserStatus() == UserStatus.WITHDRAWAL) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자 입니다.");
        }

        // 로그인 시 액세스 토큰 및 리프레시 토큰 생성 및 저장
        String accessToken = jwtTokenProvider.generateToken(user.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId());
        jwtTokenProvider.addToken(accessToken,  response);
        //토큰 자동 저장

        user.setRefresh_token(refreshToken);
        userRepository.save(user);

        return "어서오세요"+requestDto.getUsername() + "님 로그인이 완료되었습니다";
    }

    // 이메일 검사 후 상태 변경.
    @Transactional
    public void verifyEmail(String email, String code) {
        EmailVerification emailVerification = emailVerificationRepository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 일치하지 않습니다."));

        emailVerification.setVerified(true);
        emailVerificationRepository.save(emailVerification);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저 이메일이 올바르지 않습니다."));

        // 유저 상태코드를 활성화로 변경
        user.setUserStatus(UserStatus.ACTIVE);
        // 변경된 상태코드를 유저 객체에 저장
        userRepository.save(user);
    }

    // 로그아웃 메서드
    // 로그아웃시 리프레쉬 토큰 없애기.
    public String logoutUser(HttpServletResponse response) {
        try {
            User user = jwtTokenProvider.getTokenUser((HttpServletRequest) response);
            user.setRefresh_token(null); // 로그아웃시 리프레쉬 토큰 null 하기.
            userRepository.save(user);
            jwtTokenProvider.deleteCookie(response);
        } catch (Exception e) {
            System.out.println("로그아웃 과정에서 예외 발생: " + e.getMessage());
            throw e;
        }
        return "로그아웃 완료";
    }

    // 회원 탈퇴 메서드
    public String deleteUser( UserRequestDto userRequestDto,HttpServletResponse response) {
        User user = jwtTokenProvider.getTokenUser((HttpServletRequest) response);
        System.out.println("회원 탈퇴 요청을 받았습니다: " + user.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("유저 아이디가 올바르지 않습니다.");
        }

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("유저 비밀번호가 올바르지 않습니다.");
        }

        if (user.getUserStatus() == UserStatus.WITHDRAWAL) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }

        user.setUserStatus(UserStatus.WITHDRAWAL);
        userRepository.save(user);
        System.out.println("사용자 " + user.getUsername() + "가 성공적으로 탈퇴되었습니다.");
        return "회원탈퇴가 완료되었습니다 " + user.getUsername() + "님\n 안녕을 기원합니다.";
    }


}