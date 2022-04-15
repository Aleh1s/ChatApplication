package com.palamar.chatapplication.body.response;

import com.palamar.chatapplication.entity.user.Gender;

public record UserProfileDataResponse(
        String email,
        String username,
        String description,
        String phoneNumber,
        Gender gender
) {
}
