package com.chat.whispr.controller;

import com.chat.whispr.model.MessageDTO;
import com.chat.whispr.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @MessageMapping("/all")
    @SendTo("/topic/message")
    public List<MessageDTO> getMessage(String userId, String chatId) {
        return service.getAllMessage(chatId, userId);
    }

    @MessageMapping("/send")
    @SendTo("/topic/message")
    public MessageDTO sendMessage(String chatId, String userId, String msg) {
        return service.sendMessage(chatId, userId, msg);
    }

    @MessageMapping("/received")
    @SendTo("/topic/message")
    public MessageDTO markReceived(String messageId) {
        return service.markReceived(messageId);
    }

    @MessageMapping("/read")
    @SendTo("/topic/message")
    public MessageDTO markRead(String messageId) {
        return service.markRead(messageId);
    }
}
