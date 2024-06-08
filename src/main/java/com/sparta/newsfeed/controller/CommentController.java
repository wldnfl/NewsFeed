package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.CommentDto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentDto.CommentResponseDto;
import com.sparta.newsfeed.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    @Operation(summary = "댓글 생성")
    public String create_comment(
            HttpServletRequest servletRequest,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create_comment(servletRequest,boardId,commentRequestDto);
    }

    @GetMapping("/{boardId}/comment")
    @Operation(summary = "개시판의 댓글 조회")
    public List<CommentResponseDto> board_comment(
            @PathVariable Long boardId){
        return commentService.board_comment(boardId);
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    @Operation(summary = "댓글 수정")
    public String update_comment(
            HttpServletRequest servletRequest,
            @PathVariable Long commentId,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.update_comment(servletRequest,boardId,commentId, commentRequestDto);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    @Operation(summary = "댓글 삭재")
    public String delete_comment(
            HttpServletRequest servletRequest,@PathVariable long commentId, @PathVariable long boardId) {
        return commentService.delete(servletRequest,commentId);
    }
}
