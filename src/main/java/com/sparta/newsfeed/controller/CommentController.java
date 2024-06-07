package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.CommentDto.CommentRequestDto;
import com.sparta.newsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    public String create_comment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.create_comment(boardId,commentRequestDto);
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    public String update_comment(
            @PathVariable Long commentId,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto){
        return commentService.update_comment(boardId,commentId, commentRequestDto);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public String delete_comment(@PathVariable long commentId, @PathVariable long boardId) {
        return commentService.delete(commentId);
    }
}
