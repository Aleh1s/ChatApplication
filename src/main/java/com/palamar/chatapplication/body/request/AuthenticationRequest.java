package com.palamar.chatapplication.body.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
