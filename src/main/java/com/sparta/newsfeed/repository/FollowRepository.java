package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Follow;
import com.sparta.newsfeed.entity.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);
    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);
}