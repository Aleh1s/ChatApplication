package com.palamar.chatapplication.service;

import com.palamar.chatapplication.entity.ChatEntity;
import com.palamar.chatapplication.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatEntity getChatByChatBetweenJoinFetchMessages(String member1, String member2) {
        return chatRepository.findChatEntityByChatBetweenJoinFetchMessages(member1 + "-" + member2, member2 + "-" + member1)
                .orElseThrow(() -> new IllegalArgumentException("chat does not exist"));
    }

}
