package com.sparta.newsfeed.controller;

import com.sparta.newsfeed.dto.BoardRequestDto;
import com.sparta.newsfeed.dto.BoardResponseDto;
import com.sparta.newsfeed.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board/create")
    @Operation(summary = "개시물 생성")
    public String create_board(
            HttpServletRequest servletRequest, @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.create_board(servletRequest,boardRequestDto);
    }

    @GetMapping("/board/all")
    @Operation(summary = "개시물 전체 조회")
    public List<BoardResponseDto> get_all_board(HttpServletRequest servletRequest) {
        return boardService.get_all_board(servletRequest);
    }

    @GetMapping("/board/one")
    @Operation(summary = "개시물 특정 조회")
    public BoardResponseDto get_board(
            HttpServletRequest servletRequest,@RequestBody BoardRequestDto boardRequestDto) {
        return boardService.get_board(servletRequest ,boardRequestDto);
    }

    @DeleteMapping("/board/delete")
    @Operation(summary = "개시물 삭제")
    public String delete_board(HttpServletRequest servletRequest , BoardRequestDto boardRequestDto) {
        return boardService.delete_board(servletRequest,boardRequestDto);
    }

    @PatchMapping("/board/update")
    @Operation(summary = "개시물 수정")
    public String update_board(HttpServletRequest servletRequest , BoardRequestDto boardRequestDto) {
        return boardService.update_board(servletRequest , boardRequestDto);
    }
}
