package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.user.UserEntity;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    boolean existsUserEntityByEmail(String email);

    Optional<UserEntity> findUserEntityByUsername(String username);

}
