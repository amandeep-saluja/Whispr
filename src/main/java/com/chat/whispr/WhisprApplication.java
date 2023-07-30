package com.chat.whispr;

import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.service.ChatService;
import com.chat.whispr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@Slf4j
public class WhisprApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WhisprApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		UserDTO userDTO = userService.createUser("Amandeep");
		log.info("user created {}",userDTO);
		ChatService chatService = context.getBean(ChatService.class);
		ChatDTO chatDTO = chatService.createChatRoom(new HashSet<>(userDTO.getChatId()), "My chat room");
		log.info("chat room created {}", chatDTO);
	}

}
