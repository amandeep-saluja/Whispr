package com.chat.whispr.collections;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    @Id
    private String id;
    private String content;
    private String chatId;
    private String senderId;
    private String creationDateTime;
    private List<String> readUserId;
    private List<String> receivedUserId;
}
