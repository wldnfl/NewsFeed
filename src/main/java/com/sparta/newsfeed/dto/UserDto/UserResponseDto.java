package com.sparta.newsfeed.dto.UserDto;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String one_liner;
}
