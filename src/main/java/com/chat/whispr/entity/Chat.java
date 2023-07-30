package com.chat.whispr.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String groupName;

    @ElementCollection
    @CollectionTable(name = "UserChat", joinColumns = @JoinColumn(name = "chat_id"))
    @Column(name = "user_id")
    private Set<String> userIds = new HashSet<>();

    public void addUser(String userId) {
        userIds.add(userId);
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
