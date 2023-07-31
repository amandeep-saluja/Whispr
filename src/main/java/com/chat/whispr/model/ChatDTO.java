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
    private List<UserDTO> users;
    private String groupName;

    public static ChatDTO getChatDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setId(chat.getId());
        dto.setGroupName(chat.getGroupName());
        dto.setUsers(UserDTO.getUserDTOList(chat.getUsers()));
        return dto;
    }

    public static List<ChatDTO> getChatDTOList(List<Chat> Chats) {
        if(null!=Chats) {
            return Chats.stream().map(ChatDTO::getChatDTO).collect(Collectors.toList());
        }
        return null;
    }
}
