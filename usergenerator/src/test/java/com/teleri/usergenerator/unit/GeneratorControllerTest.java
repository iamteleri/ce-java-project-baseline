package com.teleri.usergenerator.unit;

import com.teleri.usergenerator.controller.GeneratorController;
import com.teleri.usergenerator.service.UserDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneratorControllerTest {

    @Mock UserDataService service;
    @InjectMocks GeneratorController controller;

    @Test
    public void whenGenerateUserAndNoErrorThenReturnsNoContent() {
        var result = controller.generateUser();
        assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    public void whenGenerateUserAndThrowsErrorThenReturnsNoContent() throws Exception {
        Mockito.doThrow(new Exception()).when(service).getAndSendUserData();
        var result = controller.generateUser();
        assertEquals(ResponseEntity.internalServerError().build(), result);
    }

}
