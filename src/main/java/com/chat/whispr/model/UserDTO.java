package com.chat.whispr.model;

import lombok.*;

import java.time.LocalDateTime;

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
}
