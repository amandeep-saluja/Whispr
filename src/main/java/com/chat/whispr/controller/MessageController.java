package com.chat.whispr.controller;

import com.chat.whispr.collections.Chat;
import com.chat.whispr.collections.Message;
import com.chat.whispr.repository.mongo.ChatRepository;
import com.chat.whispr.service.mongoImpl.MessageServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MessageController {
    private final ChatRepository chatRepository;

    private final MessageServiceImpl service;

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageServiceImpl service, SimpMessagingTemplate messagingTemplate,
                             ChatRepository chatRepository) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/all")
    public List<Message> getMessage(@Payload Message message) {
        String userId = message.getSenderId();
        String chatId = message.getChatId();
        log.info("Fetch all message API called for userID {} and chatID {}", userId, chatId);
        List<Message> messages = service.getAllMessage(chatId);
        messagingTemplate.convertAndSendToUser(userId, "/message", messages);
        return messages;
    }
    
    @MessageMapping("/send")
    public Message sendMessage(@Payload Message message) {
        String userId = message.getSenderId();
        String chatId = message.getChatId();
        String msg = message.getContent();
        log.info("send message API called with msg {} for userID {} and chatID {}", msg, userId, chatId);

        Message generatedMessage = service.sendMessage(chatId, userId, msg);
        Optional<Chat> chat = chatRepository.findById(chatId);
        chat.ifPresent(value -> value.getUserDetails()
                .forEach(user -> messagingTemplate.convertAndSendToUser(user.getUserId(), "/message", generatedMessage)));
        return generatedMessage;
    }

    @MessageMapping("/received")
    public List<String> markReceived(@Payload String dataJson) {
        Gson gson = new Gson();
        JsonDataObject data = gson.fromJson(dataJson, JsonDataObject.class);

        List<String> messageIds = data.messageIds;
        String userId = data.userId;
        String chatId = data.chatId;

        log.info("mark received message received API called for msgID {}", messageIds);
        Optional<Chat> chat = chatRepository.findById(chatId);
        chat.ifPresent(result-> {
            data.messageIds = service.markReceived(messageIds, userId);
            result.getUserDetails().forEach(user-> messagingTemplate.convertAndSendToUser(user.getUserId(), "/message-received", data));
        });
        return messageIds;
    }


    @MessageMapping("/read")
    public List<String> markRead(@Payload String dataJson) {
        Gson gson = new Gson();
        JsonDataObject data = gson.fromJson(dataJson, JsonDataObject.class);

        List<String> messageIds = data.messageIds;
        String userId = data.userId;
        String chatId = data.chatId;

        log.info("mark read message received API called for msgID {}", messageIds);
        Optional<Chat> chat = chatRepository.findById(chatId);
        chat.ifPresent(result-> {
            data.messageIds = service.markRead(messageIds, userId);
            result.getUserDetails().forEach(user-> messagingTemplate.convertAndSendToUser(user.getUserId(), "/message-read", data));
        });
        return messageIds;
    }
    private static class JsonDataObject {
        List<String> messageIds;
        String userId;
        String chatId;
    }
}
