package com.chat.whispr.service;

import com.chat.whispr.model.MessageDTO;

import java.util.List;

public interface MessageService {

    public List<MessageDTO> getAllMessage(String chatId, String userId);

    public MessageDTO sendMessage(String chatId, String userId, String msg);

    public List<String> markReceived(List<String> messageIds);

    public List<String> markRead(List<String> messageIds);
}
