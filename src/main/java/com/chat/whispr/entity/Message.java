package com.chat.whispr.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
@Getter
@Setter
@EqualsAndHashCode
public class Message {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 1000)
    private String body;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "is_received")
    private boolean isReceived;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;

    @Column(length = 255, name = "chat_id")
    private String chatId;

    @Column(length = 255, name = "user_id")
    private String userId;
}
