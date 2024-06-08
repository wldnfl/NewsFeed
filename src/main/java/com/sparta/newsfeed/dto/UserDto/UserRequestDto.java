package com.sparta.newsfeed.dto.UserDto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private String username;
    private String email;
    private String one_liner;
    private String password;
    private String currentPassword;

}
