package com.teleri.usergenerator.service;

import com.teleri.usergenerator.model.Login;
import com.teleri.usergenerator.model.Result;
import com.teleri.usergenerator.model.UserData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.teleri.usergenerator.client.UserClient;
import com.teleri.usergenerator.event.ProducerService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDataService {
	
	 private final UserClient userClient;
	 private final ProducerService producerService;
	 private final PasswordEncoder passwordEncoder;

	public void getAndSendUserData() throws Exception {
		var userdata = userClient.getUserData().block();
		var userList = Optional.ofNullable(userdata).map(UserData::getResults).orElse(Collections.emptyList());
		userList.forEach(user -> user.getLogin().setPassword(passwordEncoder.encode("user")));
		userdata.setResults(userList);
		producerService.sendMessage(userdata);
	}

}
