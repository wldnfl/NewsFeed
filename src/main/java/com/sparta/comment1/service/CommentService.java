package com.sparta.comment1.service;

import com.sparta.comment1.dto.CommentCreateRequestDto;
import com.sparta.comment1.dto.CommentResponseDto;
import com.sparta.comment1.dto.CommentUpdateRequestDto;
import com.sparta.comment1.entity.Comment;
import com.sparta.comment1.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

//    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;



    //생성
    public CommentResponseDto save(Long id, CommentCreateRequestDto request){

//      Board board = boardService.findBoardById(boardId);
        Comment comment = commentRepository.save(new Comment(request.getId(), request.getUser_id(), request.getBoard_user_id()));
        return CommentResponseDto.toDto(commentRepository.save(comment));
    }

    //수정
    public CommentResponseDto update(Long id, Long boardId, CommentUpdateRequestDto request) {

            Comment comment = findById(id);
            comment.update(request.getContent());
            return CommentResponseDto.toDto(commentRepository.save(comment));

    }

    //삭제
    public CommentResponseDto delete (Long id){

            Comment comment = findById(id);
            commentRepository.delete(comment);
            return CommentResponseDto.toDto(comment);

    }



    protected Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
