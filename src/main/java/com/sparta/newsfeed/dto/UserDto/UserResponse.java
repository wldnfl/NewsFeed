package com.sparta.newsfeed.dto.UserDto;

import com.sparta.newsfeed.entity.User_entity.User;

public class UserResponse {

/*    UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setOne_liner(user.getOne_liner());*/

    private Long id;
    private String username;
    private String email;
    private String One_liner;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.One_liner = user.getOne_liner();
    }
}
