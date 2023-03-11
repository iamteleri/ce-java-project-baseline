package com.teleri.userapi.event;

import com.teleri.userapi.dto.UserData;
import com.teleri.userapi.mapper.DataMapper;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.security.enums.RoleEnum;
import com.teleri.userapi.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

	final UserDataService service;
	final DataMapper mapper;

	Function<UserDataEntity, UserDataEntity> setRoles = ent -> {
		ent.setRoles(Arrays.asList(RoleEnum.ROLE_USER));
		return ent;
	};

	@KafkaListener(topics = "userdata", groupId = "default")
	public void listenGroupFoo(UserData userdata) {
	    log.info("Received Message: {}", userdata);

		Optional.ofNullable(userdata)
				.map(UserData::getResults)
				.map(List::stream)
				.flatMap(Stream::findFirst)
				.map(mapper::toEntity)
				.map(setRoles)
				.ifPresent(service::save);
	}

}
