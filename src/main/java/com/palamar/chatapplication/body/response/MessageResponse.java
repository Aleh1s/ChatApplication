package com.palamar.chatapplication.body.response;

import java.time.LocalDateTime;

public record MessageResponse(
        String text,
        String from,
        String to
) {
}
