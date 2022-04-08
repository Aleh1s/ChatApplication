package com.palamar.chatapplication.service;

import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean existsUserWithEmail(String email) {
        return userRepository.existsUserEntitiesByEmail(email);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));
    }

}
