package com.teleri.userapi.mapper;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.model.UserDataEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataMapper {

    final ModelMapper mapper;

    public UserDataEntity toEntity(Result result) {
        return mapper.map(result, UserDataEntity.class);
    }

    public Result toDto(UserDataEntity entity) {
        return mapper.map(entity, Result.class);
    }

}
