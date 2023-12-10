package com.NkosopaForum.NkosopaForum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HutechForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(HutechForumApplication.class, args);
	}

}
