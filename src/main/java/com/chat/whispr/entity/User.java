package com.chat.whispr.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String name;

    @Column
    private boolean isActive;

    @ElementCollection
    @CollectionTable(name = "UserChat", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "chat_id")
    private Set<String> chatIds = new HashSet<>();

    public void addChat(String chatId) {
        chatIds.add(chatId);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<String> getChatIds() {
        return chatIds;
    }

    public void setChatIds(Set<String> chatIds) {
        this.chatIds = chatIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}

