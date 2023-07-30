package com.chat.whispr.entity;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder
public class UserChatId implements Serializable {

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "user_id")
    private String userId;

    public UserChatId() {

    }

    public UserChatId(String chatId, String userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserChatId{" +
                "chatId='" + chatId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChatId that = (UserChatId) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, userId);
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
