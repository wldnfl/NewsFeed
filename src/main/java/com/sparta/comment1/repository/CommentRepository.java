package com.sparta.comment1.repository;

import com.sparta.comment1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
