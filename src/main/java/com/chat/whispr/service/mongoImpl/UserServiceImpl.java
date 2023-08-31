package com.chat.whispr.service.mongoImpl;

import com.chat.whispr.collections.Chat;
import com.chat.whispr.collections.Message;
import com.chat.whispr.collections.User;
import com.chat.whispr.exceptions.UserNotFoundException;
import com.chat.whispr.repository.mongo.ChatRepository;
import com.chat.whispr.repository.mongo.MessageRepository;
import com.chat.whispr.repository.mongo.UserRepository;
import com.chat.whispr.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MessageRepository messageRepository;

    public User createUser(String userName, String email, String password) {
        User user = User.builder()
                .id(Utility.generateId("user"))
                .name(userName)
                .password(password)
                .isActive(true)
                .email(email)
                .build();
        return userRepository.save(user);
    }

    public User updateUser(String userId, String userName, String password, String emailId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("No User Found with userId: "+userId));

        if(null != emailId){
            user.setEmail(emailId);
        }
        if(null != userName){
            user.setName(userName);
        }
        if(null != password) {
            user.setPassword(password);
        }

        return userRepository.save(user);
    }

    public String deleteUser(String userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("No User Found with userId: "+userId));
        userRepository.deleteById(user.getId());
        return user.getId();
    }

    public List<Chat> getAllChatRoom(String userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No User Found with userId: "+userId));
        return StreamSupport.stream(chatRepository.findAllById(user.getChatIds()).spliterator(), true)
                .collect(Collectors.toList());
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public List<User> getAllUserByIds(List<String> userIds) {
        return StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), true).collect(Collectors.toList());
    }

    public User getUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("No User Found with userId: "+userId));
    }

    public String cleanUsers() {
        Query query = new Query();
        long userCount = mongoTemplate.count(query, User.class);
        userRepository.deleteAll();
        long chatCount = mongoTemplate.count(query, Chat.class);
        chatRepository.deleteAll();
        long msgCount = mongoTemplate.count(query, Message.class);
        messageRepository.deleteAll();
        return "Cleanup completed..! Deleted Users: "+userCount+" Deleted Chats: "+chatCount+" Deleted Messages: "+msgCount;
    }

    public User login(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if(null != user && user.getPassword().equals(password)){
            log.info("User login successful {} {}", user.getId(), user.getName());
            return user;
        }
        log.info("User login failed {}", email);
        return null;
    }
}
