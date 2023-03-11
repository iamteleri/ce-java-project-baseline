package com.teleri.usergenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UsergeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsergeneratorApplication.class, args);
	}

}
