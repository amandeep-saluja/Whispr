package com.chat.whispr.service;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;

import java.util.List;
import java.util.List;

public interface UserService {

    public UserDTO createUser(String userName);

    public UserDTO updateUser(String userId, String userName);

    public UserDTO deleteUser(String userId, String userName);

    public List<ChatDTO> getAllChatRoom(String userId);

    public List<UserDTO> getAllUser();

    public UserDTO getUserById(String userId);
}
