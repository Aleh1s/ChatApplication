package com.palamar.chatapplication.userDetails;

import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }


    @Test
    void loadUserByUsername() {
        String email = "alex@gmail.com";

        UserEntity user = UserEntity.builder()
                .email("alex@gmail.com")
                .password("123")
                .username("Alex")
                .userStatus(ACTIVE)
                .userRole(USER).build();

        when(userRepository.findUserByEmail(email))
                .thenReturn(Optional.of(user));

        UserDetails created = userDetailsService.loadUserByUsername(email);

        assertThat(created.getUsername()).isEqualTo(user.getEmail());
    }
}
