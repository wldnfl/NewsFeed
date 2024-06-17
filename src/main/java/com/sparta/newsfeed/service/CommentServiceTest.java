package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentDto.CommentRequestDto;
import com.sparta.newsfeed.dto.CommentDto.CommentResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.entity.Likes.ContentsLike;
import com.sparta.newsfeed.entity.Users.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.CommentRepository;
import com.sparta.newsfeed.repository.ContentsLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private ContentsLikeRepository contentsLikeRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Board board;
    private Comment comment;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        board = new Board();
        board.setId(1L);
        board.setUser(user);

        comment = new Comment();
        comment.setId(1L);
        comment.setUser(user);
        comment.setBoard(board);
    }

    @Test
    public void testCreateComment() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setContents("Test Comment");

        String response = commentService.createComment(request, 1L, commentRequestDto);

        assertEquals("게시판 ::Test Comment라는 댓글이 입력되었습니다.", response);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void testBoardComment() {
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));
        when(commentRepository.findAllByBoard(any(Board.class))).thenReturn(List.of(comment));
        when(contentsLikeRepository.countByLikeContentsAndContents(any(), anyLong())).thenReturn(5L);

        List<CommentResponseDto> result = commentService.boardComment(board.getId());

        assertEquals(1, result.size());
        assertEquals(comment.getContents(), result.get(0).getContents());
        assertEquals(5L, result.get(0).getLikeCount());
    }

    @Test
    public void testBoardCommentView() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(contentsLikeRepository.countByLikeContentsAndContents(any(), anyLong())).thenReturn(5L);

        CommentResponseDto result = commentService.boardCommentView(board.getId(), comment.getId());

        assertEquals(comment.getContents(), result.getContents());
        assertEquals(5L, result.getLikeCount());
    }

    @Test
    public void testBoardCommentLike() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);
        when(contentsLikeRepository.existsByUser_IdAndLikeContentsAndContents(anyLong(), any(), anyLong())).thenReturn(false);
        when(contentsLikeRepository.save(any(ContentsLike.class))).thenReturn(new ContentsLike(user, comment));
        when(contentsLikeRepository.countByLikeContentsAndContents(any(), anyLong())).thenReturn(5L);

        CommentResponseDto result = commentService.boardCommentLike(request, board.getId(), comment.getId());

        assertEquals(comment.getContents(), result.getContents());
        assertEquals(5L, result.getLikeCount());
        assertEquals("좋아요를 누르셨습니다.", result.getLike_m());
    }

    @Test
    public void testBoardCommentNolike() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);
        when(contentsLikeRepository.existsByUser_IdAndLikeContentsAndContents(anyLong(), any(), anyLong())).thenReturn(true);
        when(contentsLikeRepository.findByUserAndContents(any(User.class), anyLong())).thenReturn(new ContentsLike(user, comment));
        doNothing().when(contentsLikeRepository).delete(any(ContentsLike.class));
        when(contentsLikeRepository.countByLikeContentsAndContents(any(), anyLong())).thenReturn(3L);

        CommentResponseDto result = commentService.boardCommentNolike(request, board.getId(), comment.getId());

        assertEquals(comment.getContents(), result.getContents());
        assertEquals(3L, result.getLikeCount());
        assertEquals("좋아요가 취소되었습니다.", result.getLike_m());
    }

    @Test
    public void testUpdateComment() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContents("Updated Comment");

        String response = commentService.updateComment(request, board.getId(), comment.getId(), requestDto);

        assertEquals("변경 전 :Test Comment\n변경 후 :Updated Comment", response);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    public void testDeleteComment() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        String response = commentService.delete(request, comment.getId());

        assertEquals("댓글 삭제 완료", response);
        verify(commentRepository, times(1)).delete(comment);
    }
}
