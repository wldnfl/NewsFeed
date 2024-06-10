package com.sparta.newsfeed.entity.Like;


import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Timer;
import com.sparta.newsfeed.entity.User.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ContentsLike extends Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //유저 아이디
    private Long user_id;
    //콘텐츠 ID
    private Long contents;
    //콘텐츠 유형
    @Enumerated(EnumType.STRING)
    private LikeContents likeContents;

    // 유저
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;


    public ContentsLike() {
    }

    public ContentsLike(User user, Board board) {
        this.user_id = user.getId();
        this.likeContents = LikeContents.BOARD;
        this.contents = board.getId();
    }

    public ContentsLike(User user, Comment comment) {
        this.user_id = user.getId();
        this.likeContents = LikeContents.COMMENT;
        this.contents = comment.getId();
    }
}
