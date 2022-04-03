package com.palamar.chatapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palamar.chatapplication.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageEntity {

    @Id
    @SequenceGenerator(
            name = "message_sequence_generator",
            sequenceName = "message_sequence"
    )
    @GeneratedValue(
            generator = "message_sequence_generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @JsonIgnore
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatEntity chat;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(STRING)
    private MessageStatus status;

}
