package com.sparta.comment1.dto;

public class CommentCreateRequestDto {

    private Long id;

    private Long user_id;

    private Long board_user_id;

    public CommentCreateRequestDto(Long id, Long user_id, Long board_user_id) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
    }

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getBoard_user_id() {
        return board_user_id;
    }
}
