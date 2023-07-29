package com.chat.whispr.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {
    private String id;

    private String body;

    private boolean isRead;

    private boolean isReceived;

    private LocalDateTime creationDateTime;

    private String chatId;

    private String userId;
}
