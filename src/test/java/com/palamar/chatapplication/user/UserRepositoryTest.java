package com.palamar.chatapplication.user;

import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.entity.user.UserRole;
import com.palamar.chatapplication.entity.user.UserStatus;
import com.palamar.chatapplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void getUserByUsernameTest() {
        String username = "test";
        UserEntity given = UserEntity.builder()
                .email("test@gmail.com")
                .username(username)
                .password("test")
                .userStatus(ACTIVE)
                .userRole(USER)
                .build();

        userRepository.save(given);

        UserEntity expected = userRepository.findUserEntityByUsername(username)
                .orElse(null);

        assertThat(expected.getId()).isNotNull();
        assertThat(expected.getUsername()).isEqualTo(username);
    }
}
