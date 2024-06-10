package com.sparta.newsfeed.controller.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {
    private int httpStatusCode;
    private String message;

    public ErrorResponseDto(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
