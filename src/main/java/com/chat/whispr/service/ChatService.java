package com.chat.whispr.service;

import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;

import java.util.List;
import java.util.Set;

public interface ChatService {

    public ChatDTO createChatRoom(List<String> userIds, String groupName);

    public ChatDTO addUserToChatRoom(String chatId, List<String> userId);

    public List<UserDTO> getAllUser(String chatId);
}
