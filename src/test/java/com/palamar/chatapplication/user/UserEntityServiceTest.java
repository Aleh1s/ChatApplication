package com.palamar.chatapplication.user;

import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }


    @Test
    void emailExists() {
        String email = "alex@gmail.com";

        ArgumentCaptor<String> emailCaptor =
                ArgumentCaptor.forClass(String.class);

        userService.existsUserWithEmail(email);

        verify(userRepository).existsUserEntitiesByEmail(emailCaptor.capture());

        assertThat(emailCaptor.getValue()).isEqualTo(email);
    }

    @Test
    void getUserByUsername() {
        String username = "Alex";

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        when(userRepository.findUserEntityByUsername(username))
                .thenReturn(Optional.of(new UserEntity()));

        userService.getUserByUsername(username);

        verify(userRepository).findUserEntityByUsername(usernameCaptor.capture());
        assertThat(username).isEqualTo(usernameCaptor.getValue());
    }

    @Test
    void getUserByUsernameThrow() {
        String username = "Alex";

        assertThatThrownBy(() -> userService.getUserByUsername(username))
                .hasMessageContaining("user does not exist");
    }
}
