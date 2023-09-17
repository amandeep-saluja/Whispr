package com.chat.whispr.controller;

import ch.rasc.sse.eventbus.SseEvent;
import ch.rasc.sse.eventbus.SseEventBus;
import com.chat.whispr.collections.Chat;
import com.chat.whispr.exceptions.ChatNotFoundException;
import com.chat.whispr.service.mongoImpl.ChatServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("chat")
@CrossOrigin()
@Slf4j
public class ChatController {

    private final ChatServiceImpl service;

    private final SseEventBus eventBus;

//    private final ObjectMapper objectMapper;

    public ChatController(ChatServiceImpl service, SseEventBus sseEventBus) {
        this.service = service;
        this.eventBus = sseEventBus;
    }

    /*@GetMapping("/{chatId}")
    public List<User> getAllUsers(@PathVariable String chatId) {
        return service.getAllUser(chatId);
    }*/

    @GetMapping("/register/{id}")
    public SseEmitter register(@PathVariable("id") String id) {
        log.info("User register for SSE {}", id);
        //return this.eventBus.createSseEmitter(id, SseEvent.DEFAULT_EVENT);
        final SseEmitter emitter = new SseEmitter();
        service.addEmitter(id, emitter);
        emitter.onCompletion(() -> {
            log.info("onCompletion-> SSE: {}", service.getEmitters().keySet());
            service.removeEmitter(id, emitter);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout-> SSE: {}", service.getEmitters().keySet());
            service.removeEmitter(id, emitter);
        });
        return emitter;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String msg, @RequestParam List<String> ids) {
        //this.eventBus.handleEvent(SseEvent.ofData(msg));
        service.doNotify(ids, msg);
        log.info("Message published {}", msg);
        return ResponseEntity.ok("Msg published...!");
    }

    @PostMapping("/create")
    public Chat createChatRoom(@RequestParam String creatorId, @RequestParam(name = "userIds") List<String> userIds, @RequestParam(name = "groupName", required = false) String groupName) throws JsonProcessingException {
        log.info("Creating new chat with name: {} for userIds: {}", groupName, userIds);
        Chat chat = service.createChatRoom(userIds, groupName);
        ObjectMapper objectMapper = new ObjectMapper();
        userIds.remove(creatorId);
        service.doNotify(userIds, objectMapper.writeValueAsString(chat));
        //this.eventBus.handleEvent(SseEvent.ofData("some useful data "+objectMapper.writeValueAsString(chat)));
        return chat;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteChatRoom(@RequestParam(name = "chatId") String chatId) {
        try {
            log.info("Deleting chat with chatId: {}", chatId);
            boolean flag = service.deleteChatRoom(chatId);
            if (flag)
                return ResponseEntity.ok("Chat " + chatId + " Successfully deleted");
            else
                return ResponseEntity.notFound().build();
        }catch (ChatNotFoundException cnfe) {
            log.error("Something went wrong: ", cnfe);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add-user")
    public Chat addUsersToChat(@RequestParam(name = "chatId") String chatId, @RequestParam(name = "userIds") List<String> userIds) {
        try {
            log.info("Adding users userIds {} to chat chatId {}", userIds, chatId);
            return service.addUserToChatRoom(chatId, userIds);
        }
        catch (ChatNotFoundException cnfe) {
            log.error("Something went wrong: ", cnfe);
        }
        return null;
    }

    @GetMapping("/all")
    public List<Chat> getAllChat() {
        log.info("Get all chats called");
        return service.getAllChat();
    }
}
