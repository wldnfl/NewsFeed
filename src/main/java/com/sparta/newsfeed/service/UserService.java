package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    // 유저 프로필 가져오기
    public UserResponseDto getUserProfile(Long userId){
        User user = userRepository.findById(userId).get();
        return new UserResponseDto(user);
    }

    // 프로필 변경
    public UserResponseDto updateUserProfile(Long userId, UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        // 비밀번호 변경 여부 확인 및 처리
        if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty()) {
            // 현재 비밀번호 확인
            if (!passwordEncoder.matches(userRequestDto.getCurrentPassword(), user.getPassword())) {
                throw new Exception("현재 비밀번호가 올바르지 않습니다.");
            }
            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
                throw new Exception("새 비밀번호는 현재 비밀번호와 동일할 수 없습니다.");
            }
            // 새 비밀번호를 설정
            String newPassword = userRequestDto.getPassword();
            // 비밀번호 형식이 올바르지 않은 경우 예외 처리
            if (!isValidPasswordFormat(newPassword)) {
                throw new Exception("올바르지 않은 비밀번호 형식입니다");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        user.update(userRequestDto);
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