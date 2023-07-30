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
    private Set<String> chatId;

    public static UserDTO getUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setId(user.getId());
        dto.setActive(user.isActive());
        dto.setLastActive(LocalDateTime.now());
        dto.setChatId(user.getChatIds());
        return dto;
    }

    public static Set<UserDTO> getUserDTOSet(Set<User> users) {
        if(null!=users) {
            return users.stream().map(UserDTO::getUserDTO).collect(Collectors.toSet());
        }
        return null;
    }
}
