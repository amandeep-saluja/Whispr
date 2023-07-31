package com.chat.whispr.repository;

import com.chat.whispr.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findMessageByChatIdAndUserId(String chatId, String userId);
}

