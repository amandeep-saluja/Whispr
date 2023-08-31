package com.chat.whispr;

import com.chat.whispr.collections.Chat;
import com.chat.whispr.collections.User;
import com.chat.whispr.service.mongoImpl.ChatServiceImpl;
import com.chat.whispr.service.mongoImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
@Slf4j
public class WhisprApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhisprApplication.class, args);
	}

}
