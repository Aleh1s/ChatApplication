package com.palamar.chatapplication.repository;

import com.palamar.chatapplication.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Boolean existsImageByUserUsername(String username);
}
