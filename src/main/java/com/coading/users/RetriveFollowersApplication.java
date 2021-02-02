package com.coading.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RetriveFollowersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetriveFollowersApplication.class, args);
	}

}
