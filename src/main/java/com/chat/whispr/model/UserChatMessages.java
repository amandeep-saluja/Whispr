package com.chat.whispr.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
public class UserChatMessages {
    List<String> messageIds;
    String userId;
    String chatId;
}
