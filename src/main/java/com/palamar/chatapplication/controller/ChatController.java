package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@CrossOrigin("http://localhost:3000")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{firstChatMemberUsername}/{secondChatMemberUsername}")
    public ResponseEntity<Object> getMutualChatByUsernames(
            @PathVariable String firstChatMemberUsername,
            @PathVariable String secondChatMemberUsername
    ) {
        return new ResponseEntity<>(
                chatService.getMutualChatFetchMessagesByUsernames(
                        firstChatMemberUsername,
                        secondChatMemberUsername
                ), HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getChatsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(chatService.getChatsDataByUsername(username), HttpStatus.ACCEPTED);
    }

}
