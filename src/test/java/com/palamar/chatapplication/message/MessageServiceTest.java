package com.palamar.chatapplication.message;

import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.MessageRepository;
import com.palamar.chatapplication.service.MessageService;
import com.palamar.chatapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    private MessageService messageService;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private UserService userService;
    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp() {
        messageService = new MessageService(chatRepository, userService, messageRepository);
    }

    @Test
    void saveMessage() {

    }
}
