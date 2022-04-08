package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> getChatById(Long id);



}
