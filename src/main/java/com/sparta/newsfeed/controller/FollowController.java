package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{followeeId}")
    @Operation(summary = "팔로우", tags = {"팔로우"})
    public String followUser(@PathVariable Long followeeId, HttpServletRequest request) {
        return followService.followUser(followeeId, request);
    }

    @DeleteMapping("/follow/{followeeId}")
    @Operation(summary = "언팔로우", tags = {"팔로우"})
    public String unfollowUser(@PathVariable Long followeeId, HttpServletRequest request) {
        return followService.unfollowUser(followeeId, request);
    }

    @GetMapping("/follow/status/{followeeId}")
    @Operation(summary = "팔로우 상태 확인", tags = {"팔로우"})
    public boolean isFollowing(@PathVariable Long followeeId, HttpServletRequest request) {
        return followService.isFollowing(followeeId, request);
    }
}
