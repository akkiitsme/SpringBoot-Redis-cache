package com.redispubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedispubsubApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedispubsubApplication.class, args);
		System.out.println("Redis-Pub-sub App is running...");
	}

}
