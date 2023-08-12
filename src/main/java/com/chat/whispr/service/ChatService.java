package com.chat.whispr.service;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;

import java.util.List;
import java.util.Set;

public interface ChatService {

    public Chat createChatRoom(List<String> userIds, String groupName);

    public boolean deleteChatRoom(String chatId);

    public Chat addUserToChatRoom(String chatId, List<String> userId);

    public List<User> getAllUser(String chatId);

    public List<Chat> getAllChat();
}
