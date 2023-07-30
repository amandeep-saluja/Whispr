package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.repository.ChatRepository;
import com.chat.whispr.repository.UserChatRepository;
import com.chat.whispr.service.ChatService;
import com.chat.whispr.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    ChatRepository chatRepository;

    UserChatRepository userChatRepository;

    public ChatServiceImpl(@Autowired ChatRepository chatRepository, @Autowired UserChatRepository userChatRepository) {
        this.chatRepository = chatRepository;
        this.userChatRepository = userChatRepository;
    }

    @Override
    public ChatDTO createChatRoom(Set<String> userIds, String groupName) {
        Chat chat = new Chat();
        chat.setId(UUID.randomUUID().toString());
        chat.setGroupName(Utility.splitCamelCase(groupName.trim()));
        chat.setUserIds(userIds);
        chatRepository.save(chat);
        return ChatDTO.getChatDTO(chat);
    }

    @Override
    public ChatDTO addUserToChatRoom(String chatId, String userId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) {
            chat.get().getUserIds().add(userId);
            chatRepository.save(chat.get());
            return ChatDTO.getChatDTO(chat.get());
        }
        return null;
    }

    @Override
    public Set<String> getAllUser(String chatId) {
        return userChatRepository.findAllUserByChatId(chatId);
    }
}
