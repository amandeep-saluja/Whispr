package com.chat.whispr.controller;

import com.chat.whispr.collections.Chat;
import com.chat.whispr.collections.User;
import com.chat.whispr.exceptions.UserNotFoundException;
import com.chat.whispr.repository.mongo.UserRepository;
import com.chat.whispr.service.mongoImpl.ChatServiceImpl;
import com.chat.whispr.service.mongoImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    private UserServiceImpl userService;

    private ChatServiceImpl chatService;

    public UserController(UserServiceImpl userService, ChatServiceImpl chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        log.info("trying to Login email {} and password {}", user.getEmail(), user.getPassword());
        User processedUser = userService.login(user.getEmail(), user.getPassword());
        if(processedUser != null) {
            return ResponseEntity.ok(processedUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("email id or password is wrong");
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        log.info("fetch all users");
        return userService.getAllUser();
    }

    @GetMapping("/all-chats")
    public List<Chat> getAllChatRoom(String userId) {
        try {
            log.info("All chats for userId {}", userId);
            return userService.getAllChatRoom(userId);
        }catch (UserNotFoundException exception) {
            log.error("Something went wrong: ", exception);
        }
        return null;
    }

    @GetMapping("/{userId}")
    public User findUser(@PathVariable String userId) {
        try {
            log.info("fetch user by id {}", userId);
            return userService.getUserById(userId);
        }catch (UserNotFoundException usfe) {
            log.error("Something went wrong: ",usfe);
        }
        return null;
    }

    @PostMapping("/save")
    public User createUser(@RequestBody User user) {
        log.info("Creating new User name {} and email {}", user.getName(), user.getEmail());
        return userService.createUser(user.getName(), user.getEmail(), user.getPassword());
    }

    @PostMapping("/update")
    public User updateUser(@RequestParam String userId,
                           @RequestParam(required = false) String userName,
                           @RequestParam(required = false) String password,
                           @RequestParam(required = false) String emailId) {
        try {
            log.info("Updating userId {} name {} email {} ", userId, userName, emailId);
            return userService.updateUser(userId, userName, password, emailId);
        }catch (UserNotFoundException exception) {
            log.error("Something went wrong: ", exception);
        }
        return null;
    }

    @DeleteMapping
    public String deleteUser(@RequestParam String userId) {
        try {
            log.info("Deleting userId {}", userId);
            return userService.deleteUser(userId);
        }catch (UserNotFoundException exception) {
            log.error("Something went wrong: ", exception);
        }
        return null;
    }

    @GetMapping("/byIds")
    public List<User> getAllUserByIds(@RequestParam List<String> userIds) {
        return userService.getAllUserByIds(userIds);
    }

    @GetMapping("/clean")
    public String cleanUsers() {
        return userService.cleanUsers();
    }

    @GetMapping("/mock")
    public void generateMockData() {
        User user1 = userService.createUser("John Doe", "john@doe.com", "john@doe");
        User user2 = userService.createUser("Alice Smith", "alice@smith.com", "alice@smith");
        User user3 = userService.createUser("Bob Johnson", "bob@johnson.com", "bob@johnson");

        Chat chat = chatService.createChatRoom(Arrays.asList(user1.getId(), user2.getId(), user3.getId()), "Friends Chat");

        log.info("User1: {}", user1);
        log.info("User2: {}", user2);
        log.info("User3: {}", user3);

        log.info("Chat: {}", chat);
    }
}
