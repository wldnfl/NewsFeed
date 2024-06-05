package com.sparta.comment1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.comment1.entity.Comment;

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

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getBoard_user_id() {
        return board_user_id;
    }

    public String getContent() {
        return content;
    }

    public Long getLike_count() {
        return like_count;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public CommentResponseDto(Long id, String content) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
        this.content = this.content;
        this.like_count = like_count;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                //comment.getUserId(),
                //comment.getBoardUserId(),
                comment.getContent()
                //comment.getLikeCount(),
                //comment.getCreatedAt(),
                //comment.getModifiedAt()
        );
    }
}
