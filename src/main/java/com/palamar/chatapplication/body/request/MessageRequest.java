package com.palamar.chatapplication.body.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public record MessageRequest(
        String text,
        String from,
        String to
) {
}
