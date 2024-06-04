package com.sparta.comment.service;

import com.sparta.comment.dto.CommentCreateRequsetDto;
import com.sparta.comment.dto.CommentResponseDto;
import com.sparta.comment.dto.CommentUpdateRequestDto;
import com.sparta.comment.model.Comment;
import com.sparta.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto findById(long id) {

        return null;
    }

    protected Optional<Comment> findCommentById(long id){

        return  null;
    }

    //생성
    public CommentResponseDto save(Long id, CommentCreateRequsetDto request){


       return null;
    }

    //수정
    public CommentResponseDto update(Long id, Long board_id, CommentUpdateRequestDto requestDto){
        return null;
    }

    //삭제
    public CommentResponseDto delete(Long id, Long board_id, String user_id){
        return null;
    }
}
