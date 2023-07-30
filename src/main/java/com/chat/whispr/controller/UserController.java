package com.chat.whispr.controller;

import com.chat.whispr.model.UserDTO;
import com.chat.whispr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("user")
@CrossOrigin
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public Set<UserDTO> getAllUsers() {
        log.info("fetch all users");
        return userService.getAllUser();
    }
}
