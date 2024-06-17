package com.sparta.newsfeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeed.dto.CommentDto.CommentRequestDto;
import com.sparta.newsfeed.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private CommentRequestDto commentRequestDto;

    @BeforeEach
    void setUp() {
        commentRequestDto = new CommentRequestDto("Test comment");
    }

    @Test
    void createCommentTest() throws Exception {
        // Mock CommentService behavior
        when(commentService.createComment(any(HttpServletRequest.class), any(Long.class), any(CommentRequestDto.class)))
                .thenReturn("Comment created successfully");

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/board/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment created successfully"));
    }
}
