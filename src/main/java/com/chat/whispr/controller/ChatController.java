package com.chat.whispr.controller;

import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
@CrossOrigin
public class ChatController {

    private final ChatService service;

    public ChatController(@Autowired ChatService service) {
        this.service = service;
    }

    @GetMapping("/{chatId}")
    public List<UserDTO> getAllUsers(@PathVariable String chatId) {
        return service.getAllUser(chatId);
    }

    @PostMapping("/create")
    public ChatDTO createChatRoom(@RequestParam(name = "userIds") List<String> userIds, @RequestParam(name = "groupName") String groupName) {
        return service.createChatRoom(userIds, groupName);
    }

    @PostMapping("/add-user")
    public ChatDTO addUsersToChat(@RequestParam(name = "chatId") String chatId, @RequestParam(name = "userIds") List<String> userIds) {
        return service.addUserToChatRoom(chatId, userIds);
    }
}
