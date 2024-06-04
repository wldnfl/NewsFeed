package com.sparta.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long id;

    private Long user_id;

    private Long board_user_id;

    private String content;

    private Long like_count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;
    private LocalDateTime modified_at;

    public CommentResponseDto(Long id, Long user_id, Long board_user_id, String content, Long like_count, LocalDateTime create_at, LocalDateTime modified_at) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
        this.content = content;
        this.like_count = like_count;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}
