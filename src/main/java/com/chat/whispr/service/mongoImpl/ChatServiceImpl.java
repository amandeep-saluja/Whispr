package com.chat.whispr.service.mongoImpl;

import com.chat.whispr.collections.Chat;
import com.chat.whispr.collections.ChatUserDetails;
import com.chat.whispr.collections.User;
import com.chat.whispr.exceptions.ChatNotFoundException;
import com.chat.whispr.repository.mongo.ChatRepository;
import com.chat.whispr.repository.mongo.UserRepository;
import com.chat.whispr.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChatServiceImpl {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public void doNotify(List<String> userIds, String data) {
        List<String> deadEmitters = new ArrayList<>();
        userIds.forEach(id-> {
            try {
                 emitterMap.get(id).send(SseEmitter.event().data(data).id(id));
            } catch (IOException|NullPointerException e) {
                deadEmitters.add(id);
            }
        });
        deadEmitters.forEach(emitterMap::remove);
    }

    public void addEmitter(final String id, final SseEmitter emitter) {
        emitterMap.put(id, emitter);
    }

    public void removeEmitter(final String id, final SseEmitter emitter) {
        emitterMap.remove(id, emitter);
    }

    public List<Chat> getAllChatById(List<String> chatIds) {
        return StreamSupport.stream(chatRepository.findAllById(chatIds).spliterator(), true).collect(Collectors.toList());
    }

    public Chat getChatById(String chatId) throws ChatNotFoundException {
        return chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat Not Found by chat id:" + chatId));
    }

    public Chat createChatRoom(List<String> userIds, String groupName) {
        List<User> users = StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), true).collect(Collectors.toList());
        List<ChatUserDetails> chatUserDetails = users.stream().map(user -> ChatUserDetails.builder().userId(user.getId()).userName(user.getName()).isActive(user.getIsActive()).build()).collect(Collectors.toList());

        Chat chat = Chat.builder().id(Utility.generateId("chat")).name(groupName).isGroup(null != groupName && !groupName.isEmpty()).deleted(false).userDetails(chatUserDetails).build();

        chatRepository.save(chat);
        userRepository.saveAll(users.stream().peek(user -> {
            if (null != user.getChatIds()) {
                user.getChatIds().add(chat.getId());
            } else {
                user.setChatIds(Collections.singletonList(chat.getId()));
            }
        }).collect(Collectors.toList()));
        return chat;
    }

    public Boolean deleteChatRoom(String chatId) throws ChatNotFoundException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        chat.orElseThrow(() -> new ChatNotFoundException("Chat Not Found by chat id:" + chatId));
        chatRepository.deleteById(chatId);
        Iterable<User> userIterable = userRepository.findAllById(chat.get().getUserDetails().stream().map(ChatUserDetails::getUserId).collect(Collectors.toList()));
        userRepository.saveAll(StreamSupport.stream(userIterable.spliterator(), true).peek(user->user.getChatIds().remove(chatId)).collect(Collectors.toList()));
        return true;
    }

    public Chat addUserToChatRoom(String chatId, List<String> userIds) throws ChatNotFoundException {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat Not Found by chat id:" + chatId));
        List<ChatUserDetails> chatUserDetails = StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), true).collect(Collectors.toList()).stream().map(user -> ChatUserDetails.builder().userId(user.getId()).userName(user.getName()).isActive(user.getIsActive()).build()).collect(Collectors.toList());
        chat.getUserDetails().addAll(chatUserDetails);
        return chatRepository.save(chat);
    }

    public List<Chat> getAllChat() {
        return chatRepository.findAll();
    }

}
