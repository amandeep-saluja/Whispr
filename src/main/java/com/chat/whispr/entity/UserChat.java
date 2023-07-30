package com.chat.whispr.entity;


import javax.persistence.*;

@Entity
@Table(name = "UserChat")
public class UserChat {

    @EmbeddedId
    public UserChatId id;

    public UserChatId getId() {
        return id;
    }

    public void setId(UserChatId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "id=" + id +
                '}';
    }
}
