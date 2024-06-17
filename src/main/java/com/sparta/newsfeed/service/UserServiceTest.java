package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserResponseDto;
import com.sparta.newsfeed.entity.Users.User;
import com.sparta.newsfeed.jwt.util.JwtTokenProvider;
import com.sparta.newsfeed.repository.MultimediaRepository;
import com.sparta.newsfeed.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private MultimediaRepository multimediaRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword(passwordEncoder.encode("password123"));
    }

    @Test
    public void testGetUserProfile() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        UserResponseDto response = userService.getUserProfile(request);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("testuser@example.com", response.getEmail());
    }

    @Test
    public void testUpdateUserProfile() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("newemail@example.com");
        requestDto.setUsername("newusername");
        requestDto.setPassword("password123");
        requestDto.setNewpassword("newpassword123");

        when(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(requestDto.getNewpassword())).thenReturn("encodedNewPassword");

        String response = userService.updateUserProfile(request, requestDto);

        assertEquals("수정완료 'Get' 으로 확인해 주세요", response);
        verify(userRepository).save(user);
        assertEquals("newemail@example.com", user.getEmail());
        assertEquals("newusername", user.getUsername());
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    public void testUpdateUserProfilePasswordMismatch() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setPassword("wrongpassword");
        requestDto.setNewpassword("newpassword123");

        when(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(request, requestDto);
        });

        assertEquals("유저 비밀번호가 올바르지 않습니다.", exception.getMessage());
    }

    @Test
    public void testUpdateUserProfileInvalidNewPasswordFormat() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(jwtTokenProvider.getTokenUser(request)).thenReturn(user);

        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setPassword("password123");
        requestDto.setNewpassword("short");

        when(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserProfile(request, requestDto);
        });

        assertEquals("올바르지 않은 비밀번호 형식입니다", exception.getMessage());
    }
}
