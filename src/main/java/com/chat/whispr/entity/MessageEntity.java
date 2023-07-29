package com.chat.whispr.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
@Getter
@Setter
public class MessageEntity {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 1000)
    private String body;

    @Column
    private boolean isRead;

    @Column
    private boolean isReceived;

    @Column
    private LocalDateTime creationDateTime;

    @Column(length = 255)
    private String chatId;

    @Column(length = 255)
    private String userId;
}
