package com.chat.whispr.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String name;

    @Column
    private boolean isActive;

    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(chats, user.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, chats);
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

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }
}

