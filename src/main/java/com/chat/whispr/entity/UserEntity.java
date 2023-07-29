package com.chat.whispr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String name;

    @Column
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity chat;
}

