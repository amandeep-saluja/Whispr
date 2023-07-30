package com.chat.whispr.service;

import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;

import java.util.List;
import java.util.Set;

public interface UserService {

    public UserDTO createUser(String userName);

    public UserDTO updateUser(String userId, String userName);

    public UserDTO deleteUser(String userId, String userName);

    public Set<String> getAllChatRoom(String userId);

    public Set<UserDTO> getAllUser();
}
