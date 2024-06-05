package com.sparta.comment1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private Long board_user_id;

    private String content;

    public Comment(Long id, Long user_id, Long board_user_id) {
        this.id = id;
        this.user_id = user_id;
        this.board_user_id = board_user_id;
    }

    public void update(String content){
        this.content = content;

    }
}
