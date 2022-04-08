package com.palamar.chatapplication.service;

import com.palamar.chatapplication.body.request.MessageRequest;
import com.palamar.chatapplication.body.response.MessageSavingResponse;
import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.Message;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.MessageRepository;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

import static com.palamar.chatapplication.entity.MessageStatus.SENT;

@Service
public class MessageService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    @Autowired
    public MessageService(ChatRepository chatRepository,
                          UserRepository userRepository,
                          MessageRepository messageRepository,
                          ChatService chatService) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.chatService = chatService;
    }

    @Transactional
    public Set<Message> getMessagesByUsernames(String firstChatMemberUsername, String secondChatMemberUsername) {
        UserEntity firstMember = userRepository.findUserByUsernameFetchChats(firstChatMemberUsername)
                .orElse(null);

        if (firstMember == null)
            firstMember = userRepository.findUserByUsername(firstChatMemberUsername)
                    .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        Chat mutualChat = chatService.getMutualChatByUserEntityAndUsername(firstMember, secondChatMemberUsername);

        if (mutualChat == null)
            mutualChat = chatService.createChatIfDoesNotExist(firstMember, secondChatMemberUsername);

        return mutualChat.getMessages();
    }



    @Transactional
    public void saveMessage(MessageRequest request) {
        UserEntity firstMember = userRepository.findUserByUsernameFetchChats(request.from())
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        Chat mutualChat = chatService.getMutualChatByUserEntityAndUsername(firstMember, request.to());

        Message message = Message.builder()
                .text(request.text())
                .sender(request.from())
                .receiver(request.to())
                .status(SENT)
                .createdAt(LocalDateTime.now())
                .build();

        mutualChat.addMessageToChat(message);

        messageRepository.save(message);
    }

    public Page<Message> getSortedPagingMessagesPageByChatId(Long chatId) {
        Chat currentChat = chatRepository.getChatById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("chat does not exist"));

        Long messagesNumber = messageRepository
                .countAllByChat(currentChat);
        int page = (int) ((messagesNumber / 10));

        return messageRepository
                .getAllByChat(currentChat, PageRequest.of(page, 10, Sort.by("id")));
    }

    @Transactional
    public MessageSavingResponse saveAndGetSortedPagingMessagesPageByChatId(Long chatId, MessageRequest request) {
        Chat currentChat = chatRepository.getChatById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("chat does not exist"));

        Message newMessage = Message.builder()
                .text(request.text())
                .sender(request.from())
                .receiver(request.to())
                .status(SENT)
                .createdAt(LocalDateTime.now())
                .build();

        currentChat.addMessageToChat(newMessage);
        messageRepository.save(newMessage);

        Long messagesNumber = messageRepository.countAllByChat(currentChat);
        int page = (int) ((messagesNumber / 10));

        Page<Message> messagePage =
                messageRepository.getAllByChat(currentChat, PageRequest.of(page, 10, Sort.by("id")));

        return new MessageSavingResponse(chatId, messagePage);
    }

    public Set<Message> getMessagesByChatId(Long id) {
        Chat currentChat = chatRepository.getChatById(id)
                .orElseThrow(() -> new IllegalArgumentException("chat does not exist"));

        return currentChat.getMessages();
    }

}
