package com.chat.whispr.service;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;

import java.util.List;
import java.util.List;

public interface UserService {

    public User createUser(String userName);

    public User updateUser(String userId, String userName);

    public User deleteUser(String userId, String userName);

    public List<Chat> getAllChatRoom(String userId);

    public List<User> getAllUser();

    public User getUserById(String userId);
}
