package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query("select c from ChatEntity c where c.chatBetween = :chatBetweenFromTo or c.chatBetween = :chatBetweenToFrom")
    Optional<ChatEntity> findChatEntityByChatBetween(String chatBetweenFromTo, String chatBetweenToFrom);

    @Query("select c from ChatEntity c join fetch c.messages where c.chatBetween = :chatBetweenFromTo or c.chatBetween = :chatBetweenToFrom")
    Optional<ChatEntity> findChatEntityByChatBetweenJoinFetchMessages(String chatBetweenFromTo, String chatBetweenToFrom);
}
