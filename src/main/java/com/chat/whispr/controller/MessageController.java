package com.chat.whispr.controller;

import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @MessageMapping("/all")
    @SendTo("/topic/message")
    public List<MessageDTO> getMessage(@Payload MessageDTO messageDTO) {
        String userId = messageDTO.getUserId();
        String chatId = messageDTO.getChatId();
        log.info("Fetch all message API called for userID {} and chatID {}", userId, chatId);
        return service.getAllMessage(chatId, userId);
    }

    @MessageMapping("/send")
    @SendTo("/topic/message")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        String userId = messageDTO.getUserId();
        String chatId = messageDTO.getChatId();
        String msg = messageDTO.getBody();
        log.info("send message API called with msg {} for userID {} and chatID {}", msg, userId, chatId);
        return service.sendMessage(chatId, userId, msg);
    }

    @MessageMapping("/received")
    @SendTo("/topic/message")
    public MessageDTO markReceived(@Payload MessageDTO messageDTO) {
        String messageId = messageDTO.getBody();
        log.info("mark received message received API called for msgID {}", messageId);
        return service.markReceived(messageId);
    }

    @MessageMapping("/read")
    @SendTo("/topic/message")
    public MessageDTO markRead(@Payload MessageDTO messageDTO) {
        String messageId = messageDTO.getId();
        log.info("mark read message received API called for msgID {}", messageId);
        return service.markRead(messageId);
    }
}
