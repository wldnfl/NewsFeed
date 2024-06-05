package com.sparta.comment1.controller;


import com.sparta.comment1.dto.CommentCreateRequestDto;
import com.sparta.comment1.dto.CommentResponseDto;
import com.sparta.comment1.dto.CommentUpdateRequestDto;
import com.sparta.comment1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create_comment(@PathVariable Long id, @RequestBody CommentCreateRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(id, request));
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> update_comment(@PathVariable Long id, @PathVariable Long boardId, @RequestBody CommentUpdateRequestDto request){
        return ResponseEntity.ok().body(service.update(id, boardId, request));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<String> delete_comment(@PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok().body("댓글 삭제 완료");

    }
}
