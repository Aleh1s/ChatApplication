package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByEmail(String email);

    boolean existsUserEntitiesByEmail(String email);

    Optional<UserEntity> findUserByUsername(String username);

    @Query("select u from UserEntity u join fetch u.chats where u.username = :username")
    Optional<UserEntity> findUserByUsernameFetchChats(String username);
}
