package com.palamar.chatapplication.service;

import com.palamar.chatapplication.body.response.ChatDataRequest;
import com.palamar.chatapplication.body.response.UserDataRequest;
import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.ChatRepository;
import com.palamar.chatapplication.repository.UserRepository;
import jdk.jfr.TransitionTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatService(UserRepository userRepository,
                       ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public Chat getMutualChatByUserEntityAndUsername(UserEntity firstMember, String secondChatMemberUsername) {
        Chat mutualChat = null;
        Set<Chat> firstMemberChats = firstMember.getChats();
        for (Chat currentChat : firstMemberChats) {
            Set<String> membersNames = currentChat.getMembers().stream()
                    .map(UserEntity::getUsername)
                    .collect(Collectors.toSet());
            if (membersNames.containsAll(Set.of(firstMember.getUsername(), secondChatMemberUsername))) {
                mutualChat = currentChat;
                break;
            }
        }

        return mutualChat;
    }

    public Chat getMutualChatFetchMessagesByUsernames(String firstChatMemberUsername, String secondChatMemberUsername) {
        UserEntity firstMember = userRepository.findUserByUsernameFetchChats(firstChatMemberUsername)
                .orElse(null);

        Chat mutualChat = null;

        if (firstMember != null) {
            mutualChat = getMutualChatByUserEntityAndUsername(firstMember, secondChatMemberUsername);
        }

        if (mutualChat == null) {
            mutualChat = createChatIfDoesNotExist(firstChatMemberUsername, secondChatMemberUsername);
        }

        return mutualChat;
    }

    public Chat createChatIfDoesNotExist(String firstChatMemberUsername, String secondChatMemberUsername) {
        UserEntity firstMember = userRepository.findUserEntityByUsername(firstChatMemberUsername)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        UserEntity secondMember = userRepository.findUserEntityByUsername(secondChatMemberUsername)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        Chat newChat = Chat.builder()
                .members(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .build();

        newChat.addUsersToChat(firstMember, secondMember);

        return chatRepository.save(newChat);
    }

    public Chat createChatIfDoesNotExist(UserEntity firstMember, String secondChatMemberUsername) {
        UserEntity secondMember = userRepository.findUserEntityByUsername(secondChatMemberUsername)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        Chat newChat = Chat.builder()
                .members(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .build();

        newChat.addUsersToChat(firstMember, secondMember);

        return chatRepository.save(newChat);
    }

    @Transactional
    public Set<ChatDataRequest> getChatsDataByUsername(String username) {
        UserEntity currentUser = userRepository.findUserByUsernameFetchChats(username)
                .orElseThrow(() -> new IllegalArgumentException("chat does not exist"));

        Set<Chat> chats = currentUser.getChats();

        return chats.stream()
                .map(chat -> new ChatDataRequest(chat.getId(), chat.getMembers().stream()
                        .map(member -> new UserDataRequest(member.getId(), member.getUsername()))
                        .collect(Collectors.toSet())))
                .collect(Collectors.toSet());
    }
}
