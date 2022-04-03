package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.body.request.MessageRequest;
import com.palamar.chatapplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageTestController {

    private final MessageService messageService;

    @Autowired
    public MessageTestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/test")
    public ResponseEntity<Object> test(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
