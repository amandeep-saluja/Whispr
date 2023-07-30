package com.chat.whispr.model;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ChatDTO {
    private String id;
    private Set<String> userId;
    private String groupName;

    public static ChatDTO getChatDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setId(chat.getId());
        dto.setGroupName(chat.getGroupName());
        dto.setUserId(chat.getUserIds());
        return dto;
    }

    public static Set<ChatDTO> getChatDTOSet(List<Chat> Chats) {
        if(null!=Chats) {
            return Chats.stream().map(ChatDTO::getChatDTO).collect(Collectors.toSet());
        }
        return null;
    }
}
