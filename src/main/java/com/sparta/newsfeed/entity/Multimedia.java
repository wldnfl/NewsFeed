package com.sparta.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Multimedia extends Timer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ImageUrl;

    private String MovieUrl;

    @OneToOne
    @JoinColumn(name = "board_id" )
    private Board board;


}
