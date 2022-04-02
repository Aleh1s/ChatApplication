package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@Slf4j
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/private-message")
    public MessageEntity sendMessage(@Payload MessageEntity message) {
        logger.info(message.toString());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/private", message); // /user/{name}/private
        return message;
    }
}
