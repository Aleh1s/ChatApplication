package com.palamar.chatapplication.authentication;

import com.palamar.chatapplication.JWT.TokenProvider;
import com.palamar.chatapplication.body.request.AuthenticationRequest;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static com.palamar.chatapplication.entity.user.UserStatus.BLOCKED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService(
                userRepository,
                passwordEncoder,
                tokenProvider
        );
    }


    @Test
    void testLogin() {
        AuthenticationRequest request = new AuthenticationRequest(
                "alex@gmail.com",
                "test"
        );
        String email = request.email();

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username("Alex")
                .userStatus(ACTIVE)
                .userRole(USER).build();

        when(userRepository.findUserEntityByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.password(), user.getPassword()))
                .thenReturn(true);

        ResponseEntity<Object> response = authenticationService.login(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void testLoginForBadPasswordThrowing() {
        AuthenticationRequest request = new AuthenticationRequest(
                "alex@gmail.com",
                "badPassword"
        );

        UserEntity user = UserEntity.builder()
                .email("alex@gmail.com")
                .password(passwordEncoder.encode("test"))
                .build();


        when(userRepository.findUserEntityByEmail(request.email()))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authenticationService.login(request))
                .hasMessageContaining("password is invalid");
    }


    @Test
    void testLoginForBlockedAccount() {
        AuthenticationRequest request = new AuthenticationRequest(
                "alex@gmail.com",
                anyString()
        );

        UserEntity user = UserEntity.builder()
                .email("alex@gmail.com")
                .password("")
                .userStatus(BLOCKED)
                .build();

        when(userRepository.findUserEntityByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        assertThatThrownBy(() -> authenticationService.login(request))
                .hasMessageContaining("account is blocked");
    }
}
