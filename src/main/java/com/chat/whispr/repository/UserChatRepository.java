package com.chat.whispr.repository;

import com.chat.whispr.entity.UserChat;
import com.chat.whispr.entity.UserChatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserChatRepository extends JpaRepository<UserChat, UserChatId> {

    @Query("SELECT u.id.userId FROM UserChat u JOIN u.id c WHERE c.chatId = :chatId")
    Set<String> findAllUserByChatId(String chatId);

    @Query("SELECT u.id.chatId FROM UserChat u JOIN u.id c WHERE c.userId = :userId")
    Set<String> findAllChatByUserId(String userId);
}
