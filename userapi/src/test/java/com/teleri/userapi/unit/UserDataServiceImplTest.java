package com.teleri.userapi.unit;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.repository.CustomUserDataRepository;
import com.teleri.userapi.repository.UserDataRepository;
import com.teleri.userapi.service.impl.UserDataServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDataServiceImplTest {

    @Mock
    UserDataRepository repository;
    @Mock
    CustomUserDataRepository customRepo;
    @InjectMocks
    UserDataServiceImpl service;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";
    private static final String NATIONALITY = "nationality";
    private static final Integer PAGE = 0;
    private static final Integer SIZE = 10;

    @Test
    public void whenExistsUserThenReturnTrue() {
        Mockito.when(repository.existsById(ID)).thenReturn(true);
        var response = service.existsUser(ID);
        assertTrue(response);
    }

    @Test
    public void whenNotExistsUserThenReturnFalse() {
        Mockito.when(repository.existsById(ID)).thenReturn(false);
        var response = service.existsUser(ID);
        assertFalse(response);
    }

    @Test
    public void whenGetUserThenReturnsUser() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(user));
        var response = service.getUser(ID);
        assertEquals(user, response);
    }

    @Test
    public void whenGetUserButUserDoNotExistsThenReturnsNull() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());
        var response = service.getUser(ID);
        assertEquals(null, response);
    }

    @Test
    public void whenGetUsersThenReturnsUsers() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(user));
        var response = service.getAll();
        assertEquals(Arrays.asList(user), response);
    }

    @Test
    public void whenSaveUserThenReturnUser() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Mockito.when(repository.save(user)).thenReturn(user);
        var response = service.save(user);
        assertEquals(user, response);
    }

    @Test
    public void whenDeleteUserThenInvokeDeleteMethod() {
        service.delete(ID);
        Mockito.verify(repository).deleteById(ID);
    }

    @Test
    public void whenGetFilteredThenReturnDataSet() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Filter filter = Filter.builder().name(NAME).lastname(LASTNAME).email(EMAIL)
                .nationality(NATIONALITY).page(PAGE).size(SIZE).build();
        Page<UserDataEntity> pagedResult = PageableExecutionUtils.getPage(Arrays.asList(user),
                PageRequest.of(Integer.valueOf(PAGE), Integer.valueOf(SIZE)),
                () -> 1);
        Mockito.when(customRepo.customFilters(Optional.of(filter))).thenReturn(pagedResult);
        var response = service.getFiltered(filter);
        assertEquals(pagedResult.getContent(), response.getContent());
    }

    @Test
    public void whenGetFilteredButNoDataThenReturnEmptyDataSet() {
        UserDataEntity user = UserDataEntity.builder().id(ID).build();
        Filter filter = Filter.builder().name(NAME).lastname(LASTNAME).email(EMAIL)
                .nationality(NATIONALITY).page(PAGE).size(SIZE).build();
        Page<UserDataEntity> pagedResult = PageableExecutionUtils.getPage(Collections.emptyList(),
                PageRequest.of(Integer.valueOf(PAGE), Integer.valueOf(SIZE)),
                () -> 0);
        Mockito.when(customRepo.customFilters(Optional.of(filter))).thenReturn(pagedResult);
        var response = service.getFiltered(filter);
        assertEquals(pagedResult.getContent(), response.getContent());
    }

}
