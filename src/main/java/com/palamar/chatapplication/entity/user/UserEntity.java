package com.palamar.chatapplication.entity.user;

import com.palamar.chatapplication.entity.ChatEntity;
import com.palamar.chatapplication.entity.MessageEntity;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.EnumType.STRING;

@Entity
@Setter
@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = "user_sequence_generator",
            sequenceName = "user_sequence"
    )
    @GeneratedValue(
            generator = "user_sequence_generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Enumerated(STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    @Enumerated(STRING)
    private UserRole userRole;

    @ManyToMany(mappedBy = "members")
    private Set<ChatEntity> chats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "sender")
    private Set<MessageEntity> messages = new HashSet<>();

}


