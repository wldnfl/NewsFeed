package com.sparta.comment.repository;
import com.sparta.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
