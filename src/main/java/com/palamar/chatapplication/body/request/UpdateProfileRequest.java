package com.palamar.chatapplication.body.request;

import com.palamar.chatapplication.entity.user.Gender;

import javax.annotation.Nullable;

public record UpdateProfileRequest(
        @Nullable String description,
        @Nullable String phoneNumber,
        @Nullable Gender gender
) {
}
