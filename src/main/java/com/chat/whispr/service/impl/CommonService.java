package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.Message;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonService {

    ModelMapper mapper;

    public CommonService(ModelMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Chat model mapper
     */

    public ChatDTO convertToChatDTO(Chat chat) {
        return mapper.map(chat, ChatDTO.class);
    }

    public List<ChatDTO> convertToChatDTOList(List<Chat> chats) {
        return chats.stream().map(this::convertToChatDTO).collect(Collectors.toList());
    }

    public Chat convertToEntity(ChatDTO dto) {
        return mapper.map(dto, Chat.class);
    }

    public List<Chat> convertToChatEntities(List<ChatDTO> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    /**
     * User model mapper
     */

    public UserDTO convertToUserDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public List<UserDTO> convertToUserDTOList(List<User> users) {
        return users.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    public User convertToUserEntity(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

    public List<User> convertToUserEntities(List<UserDTO> dtos) {
        return dtos.stream().map(this::convertToUserEntity).collect(Collectors.toList());
    }

    /**
     * Message Model mapper
     */

    public MessageDTO convertToMessageDTO(Message message) {
        return mapper.map(message, MessageDTO.class);
    }

    public List<MessageDTO> convertToMessageDTOList(List<Message> messages) {
        return messages.stream().map(this::convertToMessageDTO).collect(Collectors.toList());
    }

    public Message convertToMessageEntity(MessageDTO dto) {
        return mapper.map(dto, Message.class);
    }

    public List<Message> convertToMessageEntities(List<MessageDTO> dtos) {
        return dtos.stream().map(this::convertToMessageEntity).collect(Collectors.toList());
    }
}
