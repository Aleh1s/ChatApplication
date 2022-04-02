package com.palamar.chatapplication.entity;

import com.palamar.chatapplication.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {

    @Id
    @SequenceGenerator(
            name = "chat_sequence_generator",
            sequenceName = "chat_sequence"
    )
    @GeneratedValue(
            generator = "chat_sequence_generator",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = {@JoinColumn},
            inverseJoinColumns = {@JoinColumn}
    )
    private Set<UserEntity> members = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chat", orphanRemoval = true)
    private Set<MessageEntity> messages = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deletedAt;

    public void addUsersToChat(UserEntity ...users) {
        for (int i = 0; i < users.length; i++) {
            this.members.add(users[i]);
            users[i].getChats().add(this);
        }
    }

    public void removeUsersFromChat(UserEntity ...users) {
        for (int i = 0; i < users.length; i++) {
            this.members.remove(users[i]);
            users[i].getChats().remove(this);
        }
    }

    public void addMessageToChat(MessageEntity message) {
        this.messages.add(message);
        message.setChat(this);
    }

    public void removeMessageFromChat(MessageEntity message) {
        this.messages.remove(message);
        message.setChat(null);
    }

}
