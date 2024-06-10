package com.sparta.newsfeed.service;

import com.sparta.newsfeed.entity.Follow;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.repository.FollowRepository;
import com.sparta.newsfeed.repository.UserRepository;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String followUser(Long followeeId, HttpServletRequest request) {
        User follower = jwtTokenProvider.getTokenUser(request);
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 자기 자신을 팔로우하려고 할 때 예외 처리
        if (follower.getId().equals(followee.getId())) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        if (followRepository.findByFollowerAndFollowee(follower, followee).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowee(followee);
        followRepository.save(follow);

        return followee.getUsername() + "님 팔로우 성공!";
    }

    @Transactional
    public String unfollowUser(Long followeeId, HttpServletRequest request) {
        User follower = jwtTokenProvider.getTokenUser(request);
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 자기 자신을 언팔로우하려고 할 때 예외 처리
        if (follower.getId().equals(followee.getId())) {
            throw new IllegalArgumentException("자기 자신을 언팔로우할 수 없습니다.");
        }

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new IllegalArgumentException("팔로우하지 않은 사용자입니다."));

        followRepository.delete(follow);

        return followee.getUsername() + "님 언팔로우 성공!";
    }

    public boolean isFollowing(Long followeeId, HttpServletRequest request) {
        User follower = jwtTokenProvider.getTokenUser(request);
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return followRepository.findByFollowerAndFollowee(follower, followee).isPresent();
    }
}
