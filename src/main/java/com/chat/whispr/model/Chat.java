package com.chat.whispr.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Chat {
    private String id;
    private List<String> userId;
    private String groupName;
}