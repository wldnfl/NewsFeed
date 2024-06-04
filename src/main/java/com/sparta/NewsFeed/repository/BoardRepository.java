package com.sparta.NewsFeed.repository;

import com.sparta.NewsFeed.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser_id(Long id);
}
