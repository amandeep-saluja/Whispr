package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.repository.ChatRepository;
import com.chat.whispr.repository.UserRepository;
import com.chat.whispr.service.ChatService;
import com.chat.whispr.service.UserService;
import com.chat.whispr.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    ChatRepository chatRepository;

    UserRepository userRepository;

    public ChatServiceImpl(@Autowired ChatRepository chatRepository, @Autowired UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ChatDTO createChatRoom(List<String> userIds, String groupName) {
        List<User> users = userRepository.findAllById(userIds);
        Chat chat = new Chat();
        chat.setId(UUID.randomUUID().toString());
        chat.setGroupName(Utility.splitCamelCase(groupName.trim()));
        chat.setUsers(users);
        chatRepository.save(chat);
        users.forEach(user->{
            user.getChats().add(chat);
        });
        userRepository.saveAll(users);
        return ChatDTO.getChatDTO(chat);
    }

    @Override
    public ChatDTO addUserToChatRoom(String chatId, List<String> userIds) {
        List<User> user = userRepository.findAllById(userIds);
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) {
            chat.get().getUsers().addAll(user);
            chatRepository.save(chat.get());
            return ChatDTO.getChatDTO(chat.get());
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUser(String chatId) {
        Optional<Chat> chatDTO = chatRepository.findById(chatId);
        if(chatDTO.isPresent()) {
            List<User> users = chatDTO.get().getUsers();
            log.info("get all user {} by chat id {}", users, chatId);
            return UserDTO.getUserDTOList(users);
        }
        return null;
    }
}
