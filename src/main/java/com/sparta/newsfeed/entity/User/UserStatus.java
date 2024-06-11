package com.sparta.newsfeed.entity.User;

public enum UserStatus {
    ACTIVE,
    WITHDRAWAL,
    WAIT_EMAIL // 이메일 인증 전까지 임시로 사용하는 상태. 이메일 인증이 완료되면 ACTIVE 로 변경
}