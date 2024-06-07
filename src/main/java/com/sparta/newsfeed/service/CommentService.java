package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentDto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentDto.CommentResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.User_entity.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwt;

    //댓글 생성
    @Transactional
    public String create_comment(HttpServletRequest servletRequest, Long boardId, CommentRequestDto CommentRequestDto){
        Board board = getBoard(boardId);
        User user = jwt.getTokenUser(servletRequest);
        Comment comment = new Comment(CommentRequestDto,board,user);
        board.getCommentList().add(comment);
        comment.setBoard_user_id(board.getUser_id());
        comment.setBoard(board);
        commentRepository.save(comment);

        return "개시판 ::"+board.getContents() +"의\n "+comment.getContents() + "라는 댓글이 입력되었습니다.";
    }


    //댓글 조회
    @Transactional
    public List<CommentResponseDto> board_comment( Long boardId) {
        Board board = getBoard(boardId);

        if (board.getCommentList().isEmpty()) {
            throw new IllegalArgumentException( "댓글이 없습니다.");
        }

        return commentRepository.findAll().stream().filter(C -> C.getBoard().getId().equals(boardId))
                .map(CommentResponseDto ::new).toList();
    }

    //댓글 수정
    @Transactional
    public String update_comment(HttpServletRequest servletRequest,Long boardId,Long commentId, CommentRequestDto commentRequestDto) {
        getBoard(boardId);
        Comment comment = getComment(servletRequest, commentId);
        String content = comment.getContents();
        comment.update(commentRequestDto);
        return "변경 전 :"+content +"\n변경 후 :"+commentRequestDto.getContents();
    }


    //댓글 삭제
    public String delete (HttpServletRequest servletRequest, Long commentId){
        Comment comment = getComment(servletRequest, commentId);
        commentRepository.delete(comment);
            return "댓글 삭제 완료";
    }

    // 댓글 유저 확인
    private Comment getComment(HttpServletRequest servletRequest, Long commentId) {
        User user = jwt.getTokenUser(servletRequest);
        Comment comment = getComment(commentId);
        if(!user.getId().equals(comment.getUser_id())){
            throw new IllegalArgumentException("소유한 댓글이 아닙니다.");
        }
        return comment;
    }


    // id로 Board 가져오기
    private Board getBoard(Long id) {
        return  boardRepository
                .findById(id).orElseThrow(
                        ()->new IllegalArgumentException("해당 개시판이 없습니다."));

    }

    // id로 Comment 가져오기
    private Comment getComment(Long commentId) {
        return commentRepository
                .findById(commentId).orElseThrow(
                        ()-> new IllegalArgumentException("해당 개시판이 없습니다."));
    }


}
