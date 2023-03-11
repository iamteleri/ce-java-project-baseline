package com.teleri.usergenerator.unit;

import com.teleri.usergenerator.client.UserClient;
import com.teleri.usergenerator.event.ProducerService;
import com.teleri.usergenerator.model.Login;
import com.teleri.usergenerator.model.Result;
import com.teleri.usergenerator.model.UserData;
import com.teleri.usergenerator.service.UserDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class UserDataServiceTest {

    @Mock UserClient userClient;
    @Mock ProducerService producerService;
    @Mock PasswordEncoder passwordEncoder;
    @InjectMocks UserDataService service;

    @Test
    public void whenSendUserDataThenInvokesSendMessage() throws Exception {
        UserData initData = getUserData("user");
        UserData transformedData = getUserData("nouser");
        Mockito.when(userClient.getUserData()).thenReturn(Mono.just(initData));
        Mockito.when(passwordEncoder.encode("user")).thenReturn("nouser");
        service.getAndSendUserData();
        Mockito.verify(producerService).sendMessage(transformedData);
    }

    private UserData getUserData(String pass) {
        return UserData.builder()
                .results(Arrays.asList(Result.builder()
                        .login(Login.builder().password(pass).build())
                        .build()))
                .build();
    }

}
