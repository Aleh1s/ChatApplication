package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
@CrossOrigin("http://localhost:3000")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{firstMemberUsername}/{secondMemberUsername}")
    public ResponseEntity<Object> getMessagesByUsers(
            @PathVariable String firstMemberUsername,
            @PathVariable String secondMemberUsername
    ) {
        return new ResponseEntity<>(
                messageService.getMessagesByUsernames(firstMemberUsername, secondMemberUsername),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMessagesByChatId(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.getMessagesByChatId(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/pages/sort/{chatId}")
    public ResponseEntity<Object> getSortedPagingMessagesPageByChatId(@PathVariable Long chatId) {
        return new ResponseEntity<>(messageService.getSortedPagingMessagesPageByChatId(chatId), HttpStatus.ACCEPTED);
    }

}
