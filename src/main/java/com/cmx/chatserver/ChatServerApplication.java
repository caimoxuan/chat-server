package com.cmx.chatserver;

import com.cmx.chatserver.chat.service.ChatServiceStarter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.cmx.chatserver.security.dao")
@SpringBootApplication
public class ChatServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ChatServerApplication.class, args);
		ChatServiceStarter chatServiceStarter = context.getBean(ChatServiceStarter.class);
		chatServiceStarter.startWebSocket();
	}
}
