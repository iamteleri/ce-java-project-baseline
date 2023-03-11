package com.teleri.userapi.service.impl;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.repository.CustomUserDataRepository;
import com.teleri.userapi.repository.UserDataRepository;
import com.teleri.userapi.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    final UserDataRepository repository;
    final CustomUserDataRepository customRepository;

    @Override
    public Boolean existsUser(String userId) {
        return repository.existsById(userId);
    }

    @Override
    public UserDataEntity getUser(String userId) {
        return repository.findById(userId).orElse(null);
    }

    @Override
    public List<UserDataEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<UserDataEntity> getFiltered(Filter filter) {
        return customRepository.customFilters(Optional.ofNullable(filter));
    }

    @Override
    public UserDataEntity save(UserDataEntity user) {
       return repository.save(user);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
