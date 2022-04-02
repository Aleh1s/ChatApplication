package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    boolean existsUserEntityByEmail(String email);

}
