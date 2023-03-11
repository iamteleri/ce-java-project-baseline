package com.teleri.usergenerator.unit;

import com.teleri.usergenerator.event.ProducerService;
import com.teleri.usergenerator.model.UserData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ProducerServiceTest {

    @Mock KafkaTemplate<String, UserData> kafkaTemplate;
    @InjectMocks ProducerService producerService;

    @Before
    public void init() {
        ReflectionTestUtils.setField(producerService, "topic", "userdata");
    }

    @Test
    public void whenInvokedSendMessageMethodThenKafkaTemplateIsInvoked() {
        var data = UserData.builder().build();
        producerService.sendMessage(UserData.builder().build());
        Mockito.verify(kafkaTemplate).send("userdata", data);
    }
}
