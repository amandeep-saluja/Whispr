package com.chat.whispr.repository.mongo;

import com.chat.whispr.collections.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findAllByChatId(String chatId);


}
