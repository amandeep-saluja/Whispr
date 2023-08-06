package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Message;
import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.repository.MessageRepository;
import com.chat.whispr.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private CommonService service;

    public MessageServiceImpl(MessageRepository messageRepository, CommonService service) {
        this.messageRepository = messageRepository;
        this.service = service;
    }

    @Override
    public List<MessageDTO> getAllMessage(String chatId, String userId) {
        log.info("Service layer: get all messages by userId: {} and chatId: {}", userId, chatId);
        //return MessageDTO.getMessageDTOList(messageRepository.findMessageByChatIdAndUserId(chatId, userId));
        //return service.convertToMessageDTOList(messageRepository.findMessageByChatIdAndUserId(chatId, userId));
        return service.convertToMessageDTOList(messageRepository.findMessageByChatId(chatId));
    }

    @Override
    public MessageDTO sendMessage(String chatId, String userId, String msg) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setRead(false);
        message.setReceived(false);
        message.setChatId(chatId);
        message.setUserId(userId);
        message.setCreationDateTime(LocalDateTime.now());
        message.setBody(msg);
        //return MessageDTO.getMessageDTO(messageRepository.save(message));
        return service.convertToMessageDTO(messageRepository.save(message));
    }

    @Override
    public MessageDTO markReceived(String messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()) {
            message.get().setReceived(true);
            //return MessageDTO.getMessageDTO(message.get());
            return service.convertToMessageDTO(message.get());
        }
        return null;
    }

    @Override
    public MessageDTO markRead(String messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isPresent()) {
            message.get().setRead(true);
            //return MessageDTO.getMessageDTO(message.get());
            return service.convertToMessageDTO(message.get());
        }
        return null;
    }
}
