package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.User_entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCreatedTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<Board> findByUserInOrderByCreatedTimeDesc(List<User> users);
}
