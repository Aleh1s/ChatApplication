package com.palamar.chatapplication.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palamar.chatapplication.entity.Chat;
import com.palamar.chatapplication.entity.Image;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
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

    @Column
    private String description;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    @Enumerated(STRING)
    private Gender gender;

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

    @JsonIgnore
    @OneToOne(fetch = LAZY, cascade = ALL, mappedBy = "user", orphanRemoval = true)
    private Image profileImage;

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

    public void addProfileImage(Image image) {
        this.profileImage = image;
        image.setUser(this);
    }

    public void removeProfileImage(Image image) {
        this.profileImage = null;
        image.setUser(null);
    }
}


