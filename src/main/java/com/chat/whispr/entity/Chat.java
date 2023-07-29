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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "UserChat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private Set<User> users = new HashSet<>();

    // Helper method to add users to the chat
    public void addUser(User user) {
        users.add(user);
        user.getChats().add(this);
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(groupName, chat.groupName) && Objects.equals(users, chat.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, users);
    }
}
