package com.chat.whispr.model;

import com.chat.whispr.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String id;
    private String name;
    private boolean isActive;
    private LocalDateTime lastActive;
    private List<ChatDTO> chats;

    public static UserDTO getUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setId(user.getId());
        dto.setActive(user.isActive());
        dto.setLastActive(LocalDateTime.now());
        dto.setChats(ChatDTO.getChatDTOList(user.getChats()));
        return dto;
    }

    public static List<UserDTO> getUserDTOList(List<User> users) {
        if(null!=users) {
            return users.stream().map(UserDTO::getUserDTO).collect(Collectors.toList());
        }
        return null;
    }
}
