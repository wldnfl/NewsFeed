package com.sparta.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.dto.BoardDto.BoardRequestDto;
import com.sparta.newsfeed.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    private BoardRequestDto boardRequestDto;

    @BeforeEach
    void setUp() {
        boardRequestDto = new BoardRequestDto("Test contents");
    }

    @Test
    void createBoardTest() throws Exception {
        // Mock BoardService behavior
        when(boardService.createBoard(any(HttpServletRequest.class), any(BoardRequestDto.class)))
                .thenReturn("Board created successfully");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Board created successfully"));
    }

}