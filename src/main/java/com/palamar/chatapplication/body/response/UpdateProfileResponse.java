package com.palamar.chatapplication.body.response;

import com.palamar.chatapplication.entity.user.Gender;

import javax.annotation.Nullable;

public record UpdateProfileResponse(
        @Nullable String description,
        @Nullable String phoneNumber,
        @Nullable Gender gender
) {
}
