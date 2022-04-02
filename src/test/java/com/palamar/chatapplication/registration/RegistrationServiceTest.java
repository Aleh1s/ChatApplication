package com.palamar.chatapplication.registration;

import com.palamar.chatapplication.body.request.RegistrationRequest;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.entity.user.UserRole;
import com.palamar.chatapplication.entity.user.UserStatus;
import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.RegistrationService;
import com.palamar.chatapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    private RegistrationService registrationService;

    @Mock
    private UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationService(userService, passwordEncoder, userRepository);
    }


    @Test
    void register() {
        RegistrationRequest request = new RegistrationRequest(
            "Alex@gmail.com",
                "aksjdjq",
                "Alex"
        );

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(request.password())
                .username(request.username())
                .userStatus(ACTIVE)
                .userRole(USER).build();

        registrationService.register(request);

        ArgumentCaptor<UserEntity> userEntityCaptor =
                ArgumentCaptor.forClass(UserEntity.class);

        verify(userRepository).save(userEntityCaptor.capture());

        assertThat(userEntityCaptor.getValue()).isEqualTo(user);
    }
}
