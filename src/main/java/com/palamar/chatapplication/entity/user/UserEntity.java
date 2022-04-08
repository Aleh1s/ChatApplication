package com.palamar.chatapplication.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palamar.chatapplication.entity.Chat;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

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

    @JsonIgnore
    @Setter(PRIVATE)
    @ManyToMany(mappedBy = "members", fetch = LAZY)
    private Set<Chat> chats = new HashSet<>();

    public UserEntity(String email,
                      String password,
                      String username,
                      UserStatus userStatus,
                      UserRole userRole) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }
}


