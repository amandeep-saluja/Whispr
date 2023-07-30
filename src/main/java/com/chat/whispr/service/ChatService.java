package com.chat.whispr.service;

import com.chat.whispr.model.ChatDTO;

import java.util.Set;

public interface ChatService {

    public ChatDTO createChatRoom(Set<String> userIds, String groupName);

    public ChatDTO addUserToChatRoom(String chatId, String userId);

    public Set<String> getAllUser(String chatId);
}
