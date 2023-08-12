package com.chat.whispr.repository;

import com.chat.whispr.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findMessageByChatIdAndUserId(String chatId, String userId);

    List<Message> findMessageByChatId(String chatId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.isReceived = true WHERE m.id IN :messageIds")
    List<String> markAllMessagesReceived(List<String> messageIds);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.isRead = true WHERE m.id IN :messageIds")
    List<String> markAllMessagesRead(List<String> messageIds);
}

