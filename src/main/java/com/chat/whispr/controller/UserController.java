package com.chat.whispr.controller;

import com.chat.whispr.entity.User;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.repository.UserRepository;
import com.chat.whispr.service.UserService;
import com.chat.whispr.service.impl.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    private UserService userService;

    private UserRepository userRepository;

    private CommonService service;

    public UserController(UserService userService, UserRepository userRepository, CommonService service) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.service = service;
    }

    @GetMapping("all")
    public List<User> getAllUsers() {
        log.info("fetch all users");
        return userService.getAllUser();
        //return service.convertToUserDTOList(userRepository.findAll());
    }

    @GetMapping("/{userId}")
    public User findUser(@PathVariable String userId) {
        log.info("fetch user by id {}", userId);
        return userService.getUserById(userId);
    }

    @PostMapping("/save")
    public User saveUserWithChat(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        if (null != user.getChats() && !user.getChats().isEmpty()) {
            user.setChats(user.getChats().stream().peek(chat -> chat.setId(UUID.randomUUID().toString())).collect(Collectors.toList()));
        }
        return userRepository.save(user);
        //return UserDTO.getUserDTO(userRepository.save(user));
        //return service.convertToUserDTO(userRepository.save(user));
    }
}
