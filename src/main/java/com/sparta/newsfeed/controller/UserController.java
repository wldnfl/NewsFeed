package com.sparta.newsfeed.controller;


import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user/profile")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserResponseDto getUserProfile(@PathVariable Long userId) throws Exception {
        return userService.getUserProfile(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponseDto updateUserProfile(@PathVariable Long userId,
                                          @RequestBody UserRequestDto userRequestDto,
                                          Principal principal) throws Exception {
        return userService.updateUserProfile(userId, userRequestDto);
    }
}