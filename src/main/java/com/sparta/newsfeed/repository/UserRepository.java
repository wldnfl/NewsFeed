package com.sparta.newsfeed.repository;

import com.sparta.newsfeed.entity.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    Optional<User> findByEmail(String email);

}