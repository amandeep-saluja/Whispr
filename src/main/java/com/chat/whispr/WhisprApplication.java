package com.chat.whispr;

import com.chat.whispr.entity.Chat;
import com.chat.whispr.entity.User;
import com.chat.whispr.model.ChatDTO;
import com.chat.whispr.model.UserDTO;
import com.chat.whispr.repository.ChatRepository;
import com.chat.whispr.repository.UserRepository;
import com.chat.whispr.service.ChatService;
import com.chat.whispr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
@Slf4j
public class WhisprApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WhisprApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);
		
		// Create and save sample users
		User user1 = new User("1", "John Doe", true, null);
		User user2 = new User("2", "Alice Smith", true, null);
		User user3 = new User("3", "Bob Johnson", true, null);

		ChatRepository chatRepository = context.getBean(ChatRepository.class);

		// Create and save sample chats
		Chat chat1 = new Chat("100", "Family Chat", null);
		//Chat chat2 = new Chat("200", "Friends Chat", null);
		//Chat chat3 = new Chat("300", "Work Chat", null);
		
		// Add users to the chats and chats to the users
		List<Chat> user1Chats = new ArrayList<>();
		user1Chats.add(chat1);
		//user1Chats.add(chat2);
		user1.setChats(user1Chats);

		List<Chat> user2Chats = new ArrayList<>();
		user2Chats.add(chat1);
		//user2Chats.add(chat2);
		//user2Chats.add(chat3);
		user2.setChats(user2Chats);

		List<Chat> user3Chats = new ArrayList<>();
		user3Chats.add(chat1);
		//user3Chats.add(chat3);
		user3.setChats(user3Chats);



		// Add users to chats
		/*Set<User> chat1Users = new HashSet<>();
		chat1Users.add(user1);
		chat1Users.add(user3);
		chat1.setUsers(chat1Users);

		Set<User> chat2Users = new HashSet<>();
		chat2Users.add(user1);
		chat2Users.add(user2);
		chat2.setUsers(chat2Users);

		Set<User> chat3Users = new HashSet<>();
		chat3Users.add(user2);
		chat3Users.add(user3);
		chat3.setUsers(chat3Users);*/

		chatRepository.save(chat1);
		//chatRepository.save(chat2);
		//chatRepository.save(chat3);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);


		log.info("fetch all chats of user 1: {}", userRepository.findById("1"));
		log.info("fetch all chats of user 2: {}", userRepository.findById("2"));

		log.info("fetch all users of chat 100: {}", chatRepository.findById("100"));
		log.info("fetch all users of chat 200: {}", chatRepository.findById("200"));
		
//		UserService userService = context.getBean(UserService.class);
//		UserDTO userDTO = userService.createUser("Amandeep");
//		log.info("user created {}",userDTO);
//		ChatService chatService = context.getBean(ChatService.class);
//		ChatDTO chatDTO = chatService.createChatRoom(new HashSet<String>(Integer.parseInt(userDTO.getId())), "My chat room");
//		log.info("chat room created {}", chatDTO);
		
	}

}
