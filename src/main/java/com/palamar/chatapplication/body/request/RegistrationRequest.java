package com.palamar.chatapplication.body.request;

public record RegistrationRequest(
        String email,
        String password,
        String username
) {
}
