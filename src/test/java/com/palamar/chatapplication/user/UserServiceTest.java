package com.palamar.chatapplication.user;

import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

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

        userService.emailExists(email);

        verify(userRepository).existsUserEntityByEmail(emailCaptor.capture());

        assertThat(emailCaptor.getValue()).isEqualTo(email);
    }
}
