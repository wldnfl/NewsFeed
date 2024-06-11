package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Likes.ContentsLike;
import com.sparta.newsfeed.entity.Likes.LikeContents;
import com.sparta.newsfeed.entity.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsLikeRepository extends JpaRepository<ContentsLike, Long> {
    boolean existsByUser_IdAndLikeContentsAndContents(Long userId, LikeContents likeContents, Long contents);

    ContentsLike findByUserAndContents(User user, Long id);

    long countByLikeContentsAndContents(LikeContents likeContents, Long contents);
}