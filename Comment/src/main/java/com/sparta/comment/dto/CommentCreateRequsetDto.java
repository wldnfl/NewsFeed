package com.sparta.comment.dto;

public class CommentCreateRequsetDto {

    private Long id;

    private Long user_id;

    private Long board_user_id;

    private String content;

    private Long like_count;

    public CommentCreateRequsetDto(Long id, Long user_id, Long board_user_id, String content, Long like_count) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
        this.content = content;
        this.like_count = like_count;
    }
}
