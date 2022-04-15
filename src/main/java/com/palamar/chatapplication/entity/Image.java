package com.palamar.chatapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palamar.chatapplication.entity.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(
            generator = "image_generator",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            sequenceName = "image_sequence",
            name = "image_generator"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String originalFileName;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private String contentType;
    @Lob
//    @Type(type="org.hibernate.type.ImageType")
    private byte[] bytes;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn
    private UserEntity user;
    @Column
    private LocalDateTime uploadedAt;

    @PrePersist
    private void init() {
        uploadedAt = LocalDateTime.now();
    }

}
