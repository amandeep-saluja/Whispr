package com.chat.whispr.service.mongoImpl;

import com.chat.whispr.collections.Message;
import com.chat.whispr.repository.mongo.MessageRepository;
import com.chat.whispr.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Message> getAllMessage(String chatId) {
        return messageRepository.findAllByChatId(chatId);
    }

    public Message sendMessage(String chatId, String userId, String msg) {
        Message message = Message.builder()
                .id(Utility.generateId("msg"))
                .content(msg.trim())
                .creationDateTime(LocalDateTime.now().toString())
                .senderId(userId)
                .chatId(chatId)
                .build();
        return messageRepository.save(message);
    }

    public List<String> markReceived(List<String> messageIds, String userId) {
        Query query = new Query(Criteria.where("id").in(messageIds));
        Update update = new Update().addToSet("receivedUserId", userId);

        mongoTemplate.updateMulti(query, update, Message.class);
        return messageIds;
    }

    public List<String> markRead(List<String> messageIds, String userId) {
        Query query = new Query(Criteria.where("id").in(messageIds));
        Update update = new Update().addToSet("readUserId", userId);

        mongoTemplate.updateMulti(query, update, Message.class);
        return messageIds;
    }
}
