package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.BoardDto.BoardResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Follow;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.FollowRepository;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FollowRepository followRepository;
    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public List<BoardResponseDto> getFeed(HttpServletRequest request) {
        User user = jwtTokenProvider.getTokenUser(request);

        List<User> followees = followRepository.findByFollower(user)
                .stream()
                .map(Follow::getFollowee)
                .collect(Collectors.toList());

        List<Board> feed = boardRepository.findByUserInOrderByCreatedTimeDesc(followees);

        return feed.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }
}
