package com.teleri.userapi.service;

import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserDataService {
    Boolean existsUser(String userId);
    UserDataEntity getUser(String userId);
    List<UserDataEntity> getAll();
    Page<UserDataEntity> getFiltered(Filter filter);
    UserDataEntity save(UserDataEntity user);
    void delete(String id);
}
