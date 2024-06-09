package com.sparta.newsfeed.dto.CommentDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.newsfeed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;

    private Long user_id;

    private Long board_user_id;

    private String contents;

    private Long like_count;

    private String massage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;
    private LocalDateTime modified_at;


    public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.user_id = comment.getUser().getId();
    this.board_user_id = comment.getBoard().getUser().getId();
    this.contents = comment.getContents();
    this.create_at = comment.getCreated_time();
    this.modified_at = comment.getModifie_time();
    }

    public CommentResponseDto(Comment comment , String massage) {
        this.id = comment.getId();
        this.user_id = comment.getUser().getId();
        this.board_user_id = comment.getBoard().getUser().getId();
        this.contents = comment.getContents();
        this.create_at = comment.getCreated_time();
        this.modified_at = comment.getModifie_time();
        this.massage = massage;
    }


}
