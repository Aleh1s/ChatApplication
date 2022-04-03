package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.body.request.MessageRequest;
import com.palamar.chatapplication.body.response.MessageResponse;
import com.palamar.chatapplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("http://localhost:3000")
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate,
                             MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/private-message")
    public MessageResponse sendMessage(@Payload MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
        simpMessagingTemplate.convertAndSendToUser(messageRequest.to(), "/private", messageRequest); // /user/{name}/private
        return new MessageResponse(messageRequest.text(), messageRequest.from(), messageRequest.to());
    }
}
