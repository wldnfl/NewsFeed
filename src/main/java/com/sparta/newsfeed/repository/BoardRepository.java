package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser_IdInOrderByCreatedTimeDesc(List<Long> userIds);
}