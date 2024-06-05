package com.sparta.comment1.dto;

public class CommentUpdateRequestDto {

    private Long id;

    private String content;

    public CommentUpdateRequestDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        this.content = content;
        return null;
    }
}
