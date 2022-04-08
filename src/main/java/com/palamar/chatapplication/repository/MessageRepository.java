package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

    Page<Message> getAllByChat(Chat chat, Pageable pageable);

    Long countAllByChat(Chat chat);

}
