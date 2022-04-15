package com.palamar.chatapplication.body.response;

import com.palamar.chatapplication.body.request.UserDataRequest;

import java.time.LocalDateTime;
import java.util.Set;

public record ChatDataRequest(
        Long id,
        Set<UserDataRequest> members,
        LocalDateTime lastActivity
) {
}
