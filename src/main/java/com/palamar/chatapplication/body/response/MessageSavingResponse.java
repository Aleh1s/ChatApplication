package com.palamar.chatapplication.body.response;

import com.palamar.chatapplication.entity.Message;
import org.springframework.data.domain.Page;

import java.util.Set;

public record MessageSavingResponse(
        Long chatId,
        Page<Message> messages
) {
}
