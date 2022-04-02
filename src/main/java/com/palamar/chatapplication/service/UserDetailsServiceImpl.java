package com.palamar.chatapplication.service;

import com.palamar.chatapplication.entity.UserDetailsImpl;
import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
        return getUserDetails(user);
    }

    private UserDetails getUserDetails(UserEntity user) {
        return UserDetailsImpl.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus()).build();
    }
}
