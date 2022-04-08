package com.palamar.chatapplication.service;

import com.palamar.chatapplication.body.request.RegistrationRequest;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.entity.user.UserStatus;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static com.palamar.chatapplication.entity.user.UserRole.*;
import static com.palamar.chatapplication.entity.user.UserStatus.ACTIVE;

@Service
public class RegistrationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserService userService,
                               PasswordEncoder passwordEncoder,
                               UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> register (RegistrationRequest request) {

        String email = request.email();

        if (email == null) {
            throw new IllegalStateException("email must be not null");
        }

        if (userService.existsUserWithEmail(email)) {
            throw new IllegalStateException("email already exists");
        }

        UserEntity user = new UserEntity(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.username(),
                ACTIVE,
                USER
        );

        userRepository.save(user);

        return new ResponseEntity<>("User was created", HttpStatus.CREATED);
    }
}
