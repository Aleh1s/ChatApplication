package com.palamar.chatapplication.chat;

import com.google.common.collect.Sets;
import com.palamar.chatapplication.entity.ChatEntity;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.entity.user.UserRole;
import com.palamar.chatapplication.entity.user.UserStatus;
import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ChatRepositoryTest {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatRepositoryTest(ChatRepository chatRepository,
                              UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

//    @Test
//    public void findChatEntityByMembersTest() {
//        UserEntity user1 = UserEntity.builder()
//                .email("Test1")
//                .username("Test")
//                .password("test")
//                .userStatus(ACTIVE)
//                .userRole(USER).build();
//        UserEntity user2 = UserEntity.builder()
//                .email("Test2")
//                .username("Test")
//                .password("test")
//                .userStatus(ACTIVE)
//                .userRole(USER).build();
//
//        ChatEntity chat = ChatEntity.builder()
//                .chatBetween(user1.getUsername() + "-" + user2.getUsername())
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        chatRepository.save(chat);
//
//        ChatEntity expected = chatRepository.findChatEntityByChatBetween(user1.getUsername() + "-" + user2.getUsername())
//                .orElseThrow();
//
//        assertThat(expected).isNotNull();
//    }
}
