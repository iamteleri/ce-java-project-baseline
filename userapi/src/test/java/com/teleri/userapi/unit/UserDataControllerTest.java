package com.teleri.userapi.unit;

import com.teleri.userapi.controller.UserDataController;
import com.teleri.userapi.dto.Result;
import com.teleri.userapi.mapper.DataMapper;
import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.service.UserDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDataControllerTest {

    @Mock
    UserDataService service;
    @Mock
    DataMapper mapper;
    @InjectMocks
    UserDataController controller;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";
    private static final String NATIONALITY = "nationality";
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 10;

    @Test
    public void whenGetByIdAndExistsResultThenReturnOK() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Mockito.when(service.getUser(ID)).thenReturn(user);
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        var res = controller.getById(ID);
        assertEquals(ResponseEntity.ok(result), res);
    }

    @Test
    public void whenGetByIdAndNotExistsResultThenReturnNotFound() {
        Mockito.when(service.getUser(ID)).thenReturn(null);
        var res = controller.getById(ID);
        assertEquals(ResponseEntity.notFound().build(), res);
    }

    @Test
    public void whenGetByCriteriaAndExistsContentThenReturnPageWithContent() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Page<UserDataEntity> pagedResult = PageableExecutionUtils.getPage(Arrays.asList(user),
                PageRequest.of(Integer.valueOf(PAGE), Integer.valueOf(SIZE)),
                () -> 1);
        Page<Result> pagedDtos = PageableExecutionUtils.getPage(Arrays.asList(result),
                PageRequest.of(Integer.valueOf(PAGE), Integer.valueOf(SIZE)),
                () -> 1);

        Filter filter = Filter.builder().name(NAME).lastname(LASTNAME).email(EMAIL)
                .nationality(NATIONALITY).page(PAGE).size(SIZE).build();
        Mockito.when(service.getFiltered(filter)).thenReturn(pagedResult);
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        var finalRes = controller.getByCriteria(NAME, LASTNAME, EMAIL, NATIONALITY, PAGE.toString(), SIZE.toString());
        assertEquals(pagedDtos.getContent(), finalRes.getBody().getContent());
    }

    @Test
    public void whenGetByCriteriaAndNOTExistsContentThenReturnPageWithNOContent() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Page<UserDataEntity> pagedResult = PageableExecutionUtils.getPage(Collections.emptyList(),
                PageRequest.of(Integer.valueOf(PAGE), Integer.valueOf(SIZE)),
                () -> 0);
        Filter filter = Filter.builder().name(NAME).lastname(LASTNAME).email(EMAIL)
                .nationality(NATIONALITY).page(PAGE).size(SIZE).build();
        Mockito.when(service.getFiltered(filter)).thenReturn(pagedResult);
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        var finalRes = controller.getByCriteria(NAME, LASTNAME, EMAIL, NATIONALITY, PAGE.toString(), SIZE.toString());
        assertEquals(Collections.emptyList(), finalRes.getBody().getContent());
    }

    @Test
    public void whenCreateOKThenReturnNewUser() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        Mockito.when(mapper.toEntity(result)).thenReturn(user);
        Mockito.when(service.save(user)).thenReturn(user);
        var finalResponse = controller.create(result);
        assertEquals(result, finalResponse.getBody());
    }

    @Test
    public void whenCreateNotOKThenReturnBadReq() {
        Result result = Result.builder().id(ID).build();
        var finalResponse = controller.create(result);
        assertEquals(ResponseEntity.badRequest().build(), finalResponse);
    }

    @Test
    public void whenUpdateOKThenReturnUpdatedUser() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Mockito.when(service.existsUser(ID)).thenReturn(true);
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        Mockito.when(mapper.toEntity(result)).thenReturn(user);
        Mockito.when(service.save(user)).thenReturn(user);
        var finalResponse = controller.update(result, ID);
        assertEquals(HttpStatus.OK, finalResponse.getStatusCode());
        assertEquals(result, finalResponse.getBody());
    }

    @Test
    public void whenUpdateOKButUserDONOTEXISTSThenReturnNewUser() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Result result = Result.builder().id(ID).build();
        Mockito.when(service.existsUser(ID)).thenReturn(false);
        Mockito.when(mapper.toDto(user)).thenReturn(result);
        Mockito.when(mapper.toEntity(result)).thenReturn(user);
        Mockito.when(service.save(user)).thenReturn(user);
        var finalResponse = controller.update(result, ID);
        assertEquals(HttpStatus.CREATED, finalResponse.getStatusCode());
        assertEquals(result, finalResponse.getBody());
    }

    @Test
    public void whenUpdateKOThenReturnBadRequest() {
        Result result = Result.builder().id(ID).build();
        var finalResponse = controller.update(result, ID);
        assertEquals(HttpStatus.BAD_REQUEST, finalResponse.getStatusCode());
    }

    @Test
    public void whenDeleteUserThenReturnNoContent() {
        controller.delete(ID);
        Mockito.verify(service).delete(ID);
    }

}
