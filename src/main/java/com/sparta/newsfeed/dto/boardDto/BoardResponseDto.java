package com.sparta.newsfeed.dto.boardDto;

import com.sparta.newsfeed.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long board_id;
    private Long board_user_id;
    private String board_contents;
    private LocalDateTime created_time;
    private LocalDateTime modifie_time;
    private String massage;

    public BoardResponseDto(Board board) {
        this.board_id = board.getId();
        this.board_user_id = board.getUser_id();
        this.board_contents = board.getContents();
        this.created_time = board.getCreated_time();
        this.modifie_time = board.getModifie_time();
    }

    public BoardResponseDto(Board board ,String massage) {
        this.board_id = board.getId();
        this.board_user_id = board.getUser_id();
        this.board_contents = board.getContents();
        this.created_time = board.getCreated_time();
        this.modifie_time = board.getModifie_time();
        this.massage = massage;
    }

}
