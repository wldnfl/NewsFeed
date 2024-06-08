package com.sparta.newsfeed.service;


import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 유저 프로필 가져오기
    public UserResponseDto getUserProfile(HttpServletRequest request){
        User user = jwtTokenProvider.getTokenUser(request);
        return new UserResponseDto(user);
    }

    // 프로필 변경
    @Transactional
    public UserResponseDto updateUserProfile(HttpServletRequest request, UserRequestDto userRequestDto) {
        User user = jwtTokenProvider.getTokenUser(request);
        // 비밀번호 변경 여부 확인 및 처리
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            // 현재 비밀번호 확인
            if (passwordEncoder.matches(user.getPassword() , userRequestDto.getPassword())) {
                throw new IllegalArgumentException("유저 비밀번호가 올바르지 않습니다.");
            }

            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (passwordEncoder.matches(user.getPassword() , userRequestDto.getNewpassword())) {
                throw new IllegalArgumentException("새 비밀번호는 현재 비밀번호와 동일할 수 없습니다.");
            }

            // 새 비밀번호를 설정
            String newPassword = userRequestDto.getPassword();
            // 비밀번호 형식이 올바르지 않은 경우 예외 처리
            if (!isValidPasswordFormat(newPassword)) {
                throw new IllegalArgumentException("올바르지 않은 비밀번호 형식입니다");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            System.out.println("비밀번호 변경 완료");
        }
        if(userRequestDto.getEmail()!=null)user.setEmail(userRequestDto.getEmail());
        if(userRequestDto.getUsername()!=null)user.setUsername(userRequestDto.getUsername());
        if(userRequestDto.getOne_liner()!=null)user.setOne_liner(userRequestDto.getOne_liner());

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    // 비밀번호 형식이 올바른지 확인하는 메서드
    private boolean isValidPasswordFormat(String password) {
        // 비밀번호가 8자 이상, 숫자와 영문자를 혼합하여 구성되어 있는지 확인
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return password.matches(regex);
    }
}