package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findAllByBoard(Board board);
}
