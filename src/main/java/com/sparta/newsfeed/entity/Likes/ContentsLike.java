package com.sparta.newsfeed.entity.Likes;

import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Timer;
import com.sparta.newsfeed.entity.Users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ContentsLike extends Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘텐츠 ID
    private Long contents;

    // 콘텐츠 유형
    @Enumerated(EnumType.STRING)
    private LikeContents likeContents;

    // 유저
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ContentsLike(User user, Board board) {
        this.user = user;
        this.likeContents = LikeContents.BOARD;
        this.contents = board.getId();
    }

    public ContentsLike(User user, Comment comment) {
        this.user = user;
        this.likeContents = LikeContents.COMMENT;
        this.contents = comment.getId();
    }
}