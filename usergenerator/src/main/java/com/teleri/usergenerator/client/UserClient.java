package com.teleri.usergenerator.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.teleri.usergenerator.model.UserData;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserClient {

	private final WebClient webClient;

	public Mono<UserData> getUserData() {
		 return webClient
				.get()
				.uri("https://randomuser.me/api")
				.retrieve()
				.bodyToMono(UserData.class);
	}
	
}
