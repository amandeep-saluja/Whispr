package com.chat.whispr.service.impl;

import com.chat.whispr.entity.Message;
import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.repository.MessageRepository;
import com.chat.whispr.repository.UserRepository;
import com.chat.whispr.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;

    private MessageRepository messageRepository;

    private CommonService service;

    private EntityManager entityManager;

    public MessageServiceImpl(MessageRepository messageRepository, CommonService service, EntityManager entityManager,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.service = service;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
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
        message.setChatId(chatId);
        message.setUserId(userId);
        message.setCreationDateTime(LocalDateTime.now());
        message.setBody(msg);
        //return MessageDTO.getMessageDTO(messageRepository.save(message));
        return service.convertToMessageDTO(messageRepository.save(message));
    }

    /*public MessageDTO markReceived(String messageId, String userId) {
        AtomicReference<Message> message = null;
        userRepository
                .findById(userId)
                .ifPresent(user ->
            messageRepository
                    .findById(messageId)
                    .ifPresent(m -> {
                        message.set(m);
                        if(null == m.getReceivedUserIds()) {
                            message.get().setReceivedUserIds(Collections.singletonList(user));
                             return;
                        }
                        message.get().getReadUserIds().add(user);
                    })
        );
        return null;
    }*/

    @Override
    @Transactional
    public List<String> markReceived(List<String> messageIds) {

        List<String> messageIdsBatch = new ArrayList<>();
        int batchSize = 10;
        int finalRowCount = 0;

        for (String messageId : messageIds) {
            messageIdsBatch.add(messageId);
            if (messageIdsBatch.size() % batchSize == 0) {
                finalRowCount += updateReceivedMessages(messageIdsBatch);
                messageIdsBatch.clear();
            }
        }

        if (!messageIdsBatch.isEmpty()) {
            finalRowCount += updateReceivedMessages(messageIdsBatch);
        }

        if(messageIds.size() == finalRowCount) {
            return messageIds;
        }
        else {
            log.info("Something went wrong while updating received messages");
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<String> markRead(List<String> messageIds) {
        //return messageRepository.markAllMessagesRead(messageIds);

        List<String> messageIdsBatch = new ArrayList<>();
        int batchSize = 10;
        int finalRowCount = 0;

        for (String messageId : messageIds) {
            messageIdsBatch.add(messageId);
            if (messageIdsBatch.size() % batchSize == 0) {
                finalRowCount += updateReadMessages(messageIdsBatch);
                messageIdsBatch.clear();
            }
        }

        if (!messageIdsBatch.isEmpty()) {
            finalRowCount += updateReadMessages(messageIdsBatch);
        }

        if(messageIds.size() == finalRowCount) {
            return messageIds;
        }
        else {
            log.info("Something went wrong while updating read messages");
        }
        return Collections.emptyList();
    }

    private int updateReceivedMessages(List<String> messageIdsBatch) {
        int updatedCount = entityManager.createQuery("UPDATE Message m SET m.isReceived = true WHERE m.id IN :messageIds")
                .setParameter("messageIds", messageIdsBatch)
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();
        return updatedCount;
    }

    private int updateReadMessages(List<String> messageIdsBatch) {
        int updatedCount = entityManager.createQuery("UPDATE Message m SET m.isRead = true WHERE m.id IN :messageIds")
                .setParameter("messageIds", messageIdsBatch)
                .executeUpdate();
        entityManager.flush();
        entityManager.clear();
        return updatedCount;
    }
}
