package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.repository.UserRepository;
import com.chat.whispr.service.UserService;
import com.chat.whispr.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(String userName) {
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName(Utility.splitCamelCase(userName.trim()));
        userRepository.save(user);
        return UserDTO.getUserDTO(user);
    }

    @Override
    public UserDTO updateUser(String userId, String userName) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setName(Utility.splitCamelCase(userName.trim()));
            userRepository.save(user.get());
            return UserDTO.getUserDTO(user.get());
        }
        return null;
    }

    @Override
    public UserDTO deleteUser(String userId, String userName) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && user.get().getName().equalsIgnoreCase(userName)) {
            // TODO: define logic to below
            //delete the userId from chats
            //delete the message of user
            //delete the user
        }
        return null;
    }

    @Override
    public List<ChatDTO> getAllChatRoom(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            List<Chat> chats = user.get().getChats();
            log.info("get all chats {} by user id {}", chats, userId);
            return ChatDTO.getChatDTOList(chats);
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> userList = userRepository.findAll();
        return UserDTO.getUserDTOList(userList);
    }

    @Override
    public UserDTO getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(UserDTO::getUserDTO).orElse(null);
    }


}
