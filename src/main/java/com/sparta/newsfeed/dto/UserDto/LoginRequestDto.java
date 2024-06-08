package com.sparta.newsfeed.dto.UserDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
// 로그인시 회원가입에 사용한 Dto 클래스를 사용하면 아이디와 비밀번호 외에도 다수를 요구함으로 아이디와 비밀번호만 요구하는 로그인Dto 클래스
public class LoginRequestDto {

    //아이디
    @NotBlank(message = "사용자 아이디는 비워둘 수 없습니다.")
    @Size(min = 10, max = 20, message = "사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 대소문자 포함 영문과 숫자만 허용됩니다.")
    private String userId;

    // 비밀번호
    @NotBlank(message = " 비밀번호는 비워둘 수 없습니다.")
    @Size(min = 10, message = "비밀번호는 최소 10글자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+])[A-Za-z\\d~!@#$%^&*()_+]{10,}$"
            , message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.")
    private String password;
}
