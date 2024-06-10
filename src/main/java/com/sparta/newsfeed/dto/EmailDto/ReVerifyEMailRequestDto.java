package com.sparta.newsfeed.dto.emaildto;

import lombok.Getter;

@Getter
public class ReVerifyEMailRequestDto {
    private String email;
    private String password;
}
