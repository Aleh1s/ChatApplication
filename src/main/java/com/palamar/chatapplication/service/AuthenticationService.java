package com.palamar.chatapplication.service;

import com.palamar.chatapplication.JWT.TokenProvider;
import com.palamar.chatapplication.body.request.AuthenticationRequest;
import com.palamar.chatapplication.body.response.AuthenticationResponse;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public ResponseEntity<Object> login (AuthenticationRequest request){

        String email = request.email();
        UserEntity user = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        boolean isPasswordValid = passwordEncoder.matches(request.password(), user.getPassword());

        if(!isPasswordValid) {
            throw new IllegalStateException("password is invalid");
        }

        if(!user.getUserStatus().equals(ACTIVE)) {
            throw new IllegalStateException("account is blocked");
        }

        String token = tokenProvider.generateToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);
        AuthenticationResponse authorizationResponse =
                new AuthenticationResponse(token, refreshToken);

        return new ResponseEntity<>(authorizationResponse, HttpStatus.ACCEPTED);
    }
}
