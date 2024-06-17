package com.sparta.newsfeed.entity.Users;
import com.sparta.newsfeed.dto.UserDto.SignUpRequestDto;
import com.sparta.newsfeed.dto.UserDto.UserRequestDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreationFromSignUpRequestDto() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto("userId123", "Password1!", "username", "test@example.com", "This is a one-liner");
        User user = new User(signUpRequestDto);

        assertEquals("userId123", user.getUserId());
        assertEquals("Password1!", user.getPassword());
        assertEquals("username", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("This is a one-liner", user.getOne_liner());
        assertEquals(UserStatus.ACTIVE, user.getUserStatus());
    }

    @Test
    public void testUserUpdateFromUserRequestDto() {
        User user = new User();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("newUsername");
        userRequestDto.setEmail("newEmail@example.com");
        userRequestDto.setOne_liner("new one-liner");

        user.update(userRequestDto);

        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail@example.com", user.getEmail());
        assertEquals("new one-liner", user.getOne_liner());
    }
}
