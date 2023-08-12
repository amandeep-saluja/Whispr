package com.chat.whispr.controller;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.repository.ChatRepository;
import com.chat.whispr.service.MessageService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class MessageController {
    private final ChatRepository chatRepository;

    private final MessageService service;

    private SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService service, SimpMessagingTemplate messagingTemplate,
                             ChatRepository chatRepository) {
        this.service = service;
        this.messagingTemplate = messagingTemplate;
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/all")
    //@SendTo("/topic/message")
    public List<MessageDTO> getMessage(@Payload MessageDTO messageDTO) {
        String userId = messageDTO.getUserId();
        String chatId = messageDTO.getChatId();
        log.info("Fetch all message API called for userID {} and chatID {}", userId, chatId);
        List<MessageDTO> messages = service.getAllMessage(chatId, userId);
        messagingTemplate.convertAndSendToUser(userId, "/message", messages);
        return messages;
    }

    /*@MessageMapping("/send")
    @SendTo("/topic/message")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        String userId = messageDTO.getUserId();
        String chatId = messageDTO.getChatId();
        String msg = messageDTO.getBody();
        log.info("send message API called with msg {} for userID {} and chatID {}", msg, userId, chatId);
        return service.sendMessage(chatId, userId, msg);
    }*/

    @MessageMapping("/private-message")
    public MessageDTO receivedPrivateMessage(@Payload MessageDTO messageDTO) {
        String userId = messageDTO.getUserId();
        String chatId = messageDTO.getChatId();
        String msg = messageDTO.getBody();
        log.info("send private message API called with msg {} for userID {} and chatID {}", msg, userId, chatId);

        MessageDTO dto = service.sendMessage(chatId, userId, msg);
        Optional<Chat> chat = chatRepository.findById(chatId);
        chat.ifPresent(value -> value.getUsers().forEach(user -> messagingTemplate.convertAndSendToUser(user.getId(), "/private", dto)));
        //messagingTemplate.convertAndSendToUser(userId, "/private", dto); // /user/<userId>/private
        return dto;
    }

    @MessageMapping("/received")
    //@SendTo("/topic/message-received")
    public List<String> markReceived(@Payload String dataJson) {
        // Parse the JSON data to extract messageIds and userId
        Gson gson = new Gson();
        MyDataObject data = gson.fromJson(dataJson, MyDataObject.class);

        List<String> messageIds = data.messageIds;
        String userId = data.userId;
        String chatId = data.chatId;

        log.info("mark received message received API called for msgID {}", messageIds);
        List<String> processedMessageIds = service.markReceived(messageIds);
        data.messageIds = processedMessageIds;
        messagingTemplate.convertAndSendToUser(userId, "/message-received", data);
        return messageIds;
    }

    @MessageMapping("/read")
    //@SendTo("/topic/message-read")
    public List<String> markRead(@Payload String dataJson) {
        Gson gson = new Gson();
        MyDataObject data = gson.fromJson(dataJson, MyDataObject.class);

        List<String> messageIds = data.messageIds;
        String userId = data.userId;
        String chatId = data.chatId;

        log.info("mark read message received API called for msgID {}", messageIds);
        List<String> processedMessageIds = service.markRead(messageIds);
        messagingTemplate.convertAndSendToUser(userId, "/message-read", processedMessageIds);
        return processedMessageIds;
    }
}
class MyDataObject {
    List<String> messageIds;
    String userId;
    String chatId;
}
