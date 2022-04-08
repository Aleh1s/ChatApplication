package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.body.request.MessageRequest;
import com.palamar.chatapplication.body.response.MessageResponse;
import com.palamar.chatapplication.body.response.MessageSavingResponse;
import com.palamar.chatapplication.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("http://localhost:3000")
public class MessageWebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageWebSocketController(SimpMessagingTemplate simpMessagingTemplate,
                                      MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/private-message")
    public MessageSavingResponse sendMessage(@Payload MessageRequest messageRequest) {
        MessageSavingResponse response = messageService
                .saveAndGetSortedPagingMessagesPageByChatId(messageRequest.chatId(), messageRequest);
        simpMessagingTemplate.convertAndSendToUser(messageRequest.to(), "/private", response); // /user/{name}/private
        simpMessagingTemplate.convertAndSendToUser(messageRequest.from(), "/private", response);
        return response;
    }


}
