package com.sparta.newsfeed.dto.UserDto;

import com.sparta.newsfeed.entity.User.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String One_liner;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.One_liner = user.getOne_liner();
    }
}
