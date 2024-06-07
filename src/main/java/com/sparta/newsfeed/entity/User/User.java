package com.sparta.newsfeed.entity.User;

import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.entity.Timer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Entity
@Getter
public class User extends Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //아이디 비번
    @Column(unique = true)
    private String userId;
    private String password;
    //이름
    private String username;
    //이매일
    private String email;
    //한줄 소개
    private String one_liner;
    //리프레쉬 토큰
    private String refresh_token;

    // 유저의 상태코드. 기본값은 정상임.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public void User(SignUpRequestDto userSignRequestDto){
        this.userId = userSignRequestDto.getUserId();
        this.password = userSignRequestDto.getPassword();
        this.username = userSignRequestDto.getUsername();
        this.email = userSignRequestDto.getEmail();
        this.one_liner = userSignRequestDto.getOne_liner();
        this.userStatus = userSignRequestDto.getUserStatus();
    }

}
