package com.palamar.chatapplication.service;

import com.palamar.chatapplication.body.request.MessageRequest;
import com.palamar.chatapplication.entity.ChatEntity;
import com.palamar.chatapplication.entity.MessageEntity;
import com.palamar.chatapplication.entity.MessageStatus;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class MessageService {

    private final ChatRepository chatRepository;
    private final UserService userService;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(ChatRepository chatRepository,
                          UserService userService,
                          MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.messageRepository = messageRepository;

    }

    @Transactional
    public void saveMessage(MessageRequest messageRequest) {

        String chatBetweenFromTo = messageRequest.from() + "-" + messageRequest.to();
        String chatBetweenToFrom = messageRequest.to() + "-" + messageRequest.from();

        ChatEntity chat = chatRepository.findChatEntityByChatBetween(chatBetweenFromTo, chatBetweenToFrom)
                .orElse(new ChatEntity());

        UserEntity from = userService.getUserByUsername(messageRequest.from());
        UserEntity to = userService.getUserByUsername(messageRequest.to());

        if (chat.getId() == null) {
            chat = ChatEntity.builder()
                    .chatBetween(chatBetweenFromTo)
                    .members(new HashSet<>())
                    .messages(new HashSet<>())
                    .createdAt(LocalDateTime.now())
                    .build();

            chat = chatRepository.save(chat);

            chat.addUsersToChat(from, to);
        }


        MessageEntity message = MessageEntity.builder()
            .sender(from.getUsername())
            .receiver(to.getUsername())
            .text(messageRequest.text())
            .createdAt(LocalDateTime.now())
            .status(MessageStatus.DELIVERED)
            .build();

        chat.addMessageToChat(message);
        messageRepository.save(message);
    }
}
