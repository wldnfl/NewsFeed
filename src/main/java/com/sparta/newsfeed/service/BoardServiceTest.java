package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.BoardDto.BoardRequestDto;
import com.sparta.newsfeed.dto.BoardDto.BoardResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.entity.Users.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.BoardRepository;
import com.sparta.newsfeed.repository.ContentsLikeRepository;
import com.sparta.newsfeed.repository.MultimediaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MultimediaRepository multimediaRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ContentsLikeRepository contentsLikeRepository;

    @InjectMocks
    private BoardService boardService;

    private User user;
    private Board board;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        board = new Board();
        board.setId(1L);
        board.setUser(user);
    }

    @Test
    public void testCreateBoard() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setContents("Test Board");

        String response = boardService.createBoard(request, boardRequestDto);

        assertEquals("Test Board 생성 완료", response);
        verify(boardRepository).save(any(Board.class));
    }

    @Test
    public void testGetBoard() {
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        BoardResponseDto response = boardService.getBoard(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    public void testDeleteBoard() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setId(1L);

        String response = boardService.deleteBoard(request, boardRequestDto);

        assertEquals("삭제 완료", response);
        verify(boardRepository).delete(board);
    }

    @Test
    public void testUpdateBoard() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        BoardRequestDto boardRequestDto = new BoardRequestDto();
        boardRequestDto.setId(1L);
        boardRequestDto.setContents("Updated Board");

        String response = boardService.updateBoard(request, boardRequestDto);

        assertEquals("수정완료", response);
        assertEquals("Updated Board", board.getContents());
    }
}
