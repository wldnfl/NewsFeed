package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponse;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // 유저 프로필 가져오기
    public UserResponse getUserProfile(Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        return new UserResponse(user);
    }

    // 프로필 변경
    public UserResponse updateUserProfile(Long userId, UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

//        if (!passwordEncoder.matches(userRequest.getCurrentPassword(), user.getPassword())) {
//            throw new Exception("Current password is incorrect");
//        }
//
//        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
//            if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
//                throw new Exception("New password cannot be the same as the current password");
//            }
//            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//        }

        user.update(userRequestDto);
        userRepository.save(user);
        return new UserResponse(user);
    }
}