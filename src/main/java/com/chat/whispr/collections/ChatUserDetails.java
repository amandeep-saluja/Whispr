package com.chat.whispr.collections;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatUserDetails {
    private String userId;
    private String userName;
    private boolean isActive;
}
