package com.palamar.chatapplication.body.response;

public record AuthenticationResponse(
        String token,
        String refreshToken
) {
}
