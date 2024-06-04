package com.sparta.comment.dto;

public class CommentUpdateRequestDto {

    private Long id;

    private String content;

    public CommentUpdateRequestDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
