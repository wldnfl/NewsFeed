package com.sparta.newsfeed.entity;


import com.sparta.newsfeed.dto.BoardRequestDto;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Board extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user_id;

    private String contents;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Multimedia> multimediaList = new ArrayList<>();

    public Board() {
    }

    public Board(HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        this.user_id = boardRequestDto.getUser_id();
        this.contents = boardRequestDto.getContents();
    }

    public void update( BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }

    public void addMultimedia(Multimedia multimedia) {
        multimediaList.add(multimedia);
        multimedia.setBoard(this);
    }

    public void removeMultimedia(Multimedia multimedia) {
        multimediaList.remove(multimedia);
        multimedia.setBoard(null);
    }
}
