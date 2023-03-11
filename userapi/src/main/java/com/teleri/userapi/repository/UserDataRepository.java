package com.teleri.userapi.repository;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.dto.UserData;
import com.teleri.userapi.model.UserDataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDataRepository extends MongoRepository<UserDataEntity, String> {

}
