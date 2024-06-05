package com.sparta.newsfeed.entity;


import com.sparta.newsfeed.dto.BoardRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
@Entity
public class Board extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user_id;

    private String contents;


    public Board() {
    }

    public Board(HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        this.user_id = boardRequestDto.getUser_id();
        this.contents = boardRequestDto.getContents();
    }

    public void update( BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }
}
