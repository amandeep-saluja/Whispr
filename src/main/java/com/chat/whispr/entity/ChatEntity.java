package com.chat.whispr.entity;

import javax.persistence.*;
        import java.util.Set;

@Entity
@Table(name = "Chat")
public class ChatEntity {

    @Id
    @Column(length = 255)
    private String id;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private Set<UserEntity> users;

    @Column(length = 255)
    private String groupName;

    // Constructors, getters, setters, etc.

    // Helper method to add users to the chat
    public void addUser(UserEntity user) {
        users.add(user);
        user.setChat(this);
    }
}
