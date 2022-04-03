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

    @GetMapping("/{member1}/{member2}")
    public ResponseEntity<Object> getChatByIdJoinFetchMessage(@PathVariable String member1, @PathVariable String member2) {
        return new ResponseEntity<>(chatService.getChatByChatBetweenJoinFetchMessages(member1, member2), HttpStatus.ACCEPTED);
    }

}
