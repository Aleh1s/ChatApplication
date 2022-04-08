package com.palamar.chatapplication.message;

import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.Message;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.MessageRepository;
import com.palamar.chatapplication.repository.UserRepository;
import com.palamar.chatapplication.service.ChatService;
import com.palamar.chatapplication.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static com.palamar.chatapplication.entity.MessageStatus.DELIVERED;
import static com.palamar.chatapplication.entity.user.UserRole.USER;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatService chatService;

    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        messageService = new MessageService(chatRepository, userRepository, messageRepository, chatService);
    }


//    @Test
//    void getMessagesByUsers() {
//        // given
//        Chat currentChat = Chat.builder()
//                .id(1L)
//                .createdAt(LocalDateTime.now())
//                .messages(new HashSet<>())
//                .members(new HashSet<>())
//                .build();
//
//        UserEntity sender = UserEntity.builder()
//                .id(1L)
//                .email("sender@gmail.com")
//                .username("test")
//                .password("test")
//                .userRole(USER)
//                .userStatus(ACTIVE)
//                .chats(new HashSet<>())
//                .build();
//
//        UserEntity receiver = UserEntity.builder()
//                .id(2L)
//                .email("receiver@gmail.com")
//                .username("test")
//                .password("test")
//                .userRole(USER)
//                .userStatus(ACTIVE)
//                .chats(new HashSet<>())
//                .build();
//
//        Message message = Message.builder()
//                .id(1L)
//                .chat(currentChat)
//                .createdAt(LocalDateTime.now())
//                .status(DELIVERED)
//                .text("Test")
//                .build();
//
//        currentChat.addMessageToChat(message);
//        currentChat.addUsersToChat(sender, receiver);
//
//        // when
//        when(userRepository.findUserByUsernameFetchChats(
//                sender.getUsername()
//        ))
//                .thenReturn(Optional.of(sender));
//
//        // then
//        assertThat(messageService.getMessagesByUsernames(
//                sender.getUsername(),
//                receiver.getUsername()
//        ))
//                .isNotNull();
//    }
}
