package com.teleri.usergenerator.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.teleri.usergenerator.model.UserData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProducerService {
	
	@Value(value = "${spring.kafka.topic}")
    private String topic;

	private final KafkaTemplate<String, UserData> kafkaTemplate;

	public void sendMessage(UserData message) {
		log.info("Sending message into topic {}: {}", topic, message);
	    kafkaTemplate.send(topic, message);
	}
	
}
