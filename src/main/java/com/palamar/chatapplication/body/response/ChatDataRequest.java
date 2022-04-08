package com.palamar.chatapplication.body.response;

import com.palamar.chatapplication.entity.user.UserEntity;

import java.util.Set;

public record ChatDataRequest(
        Long id,
        Set<UserDataRequest> members
) {
}
