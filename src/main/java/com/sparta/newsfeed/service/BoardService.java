package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.BoardRequestDto;
import com.sparta.newsfeed.dto.BoardResponseDto;
import com.sparta.newsfeed.entity.Board;
import com.sparta.newsfeed.repository.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 개시판 생성
    // HttpServletRequest 는 유저 정보 받아오는거
    public String create_board(
            HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        Board board = new Board(servletRequest,boardRequestDto);
        boardRepository.save(board);
        return "생성 완료";
    }

    // 개시판 전채 조회
    // 1인 이유는 아직 유저값을 못받아서 그런것
    public List<BoardResponseDto> get_all_board(HttpServletRequest servletRequest) {
        List<Board> boards = boardRepository.findByUser_id(1L);
        if(boards.isEmpty()) throw new IllegalArgumentException("사용자의 개시물이 없습니다.");


        return boardRepository.findAll().stream()
                .filter(B -> B.getUser_id().equals(1L))
                .map( BoardResponseDto :: new)
                .toList();
    }

    // 개시판 특정 조회
    public BoardResponseDto get_board(
            HttpServletRequest servletRequest,BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        return new BoardResponseDto(board);
    }

    // 개시판 삭제
    public String delete_board(
            HttpServletRequest servletRequest,BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        boardRepository.delete(board);
        return "삭제 완료";
    }

    // 개시판 수정
    public String update_board(HttpServletRequest servletRequest, BoardRequestDto boardRequestDto) {
        Board board = getBoard(boardRequestDto);
        board.update(boardRequestDto);
        return "수정완료";
    }


    // 개시판 id로 찾아서 가셔오기
    private Board getBoard(BoardRequestDto boardRequestDto) {
        Optional<Board> boards = boardRepository.findById(boardRequestDto.getId());
        if(boards.isEmpty()) throw new NullPointerException("사용자의 개시물이 없습니다.");
        Board board = boards.get();
        return board;
    }
}
