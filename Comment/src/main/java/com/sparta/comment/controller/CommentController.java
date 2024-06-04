package com.sparta.comment.controller;

import com.sparta.comment.dto.CommentCreateRequsetDto;
import com.sparta.comment.dto.CommentResponseDto;
import com.sparta.comment.dto.CommentUpdateRequestDto;
import com.sparta.comment.model.Comment;
import com.sparta.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board/{boardId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create_comment(@PathVariable Long id, @RequestBody CommentCreateRequsetDto requset){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(id, requset));
    }

    @PatchMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> update_comment(@PathVariable Long id, @PathVariable Long board_Id, @RequestBody CommentUpdateRequestDto requset){
        return ResponseEntity.ok().body(service.update(id, board_Id, requset));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<CommentResponseDto> delete_comment(@PathVariable Long id, @PathVariable Long boardId, @RequestBody String user_id){

        service.delete(id, boardId, user_id);
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }


}
