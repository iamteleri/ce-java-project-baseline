package com.teleri.userapi.repository;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDataRepository {

    final MongoTemplate mongoTemplate;

    public Page<UserDataEntity> customFilters(Optional<Filter> optFilter) {
        Query query = new Query();
        optFilter.map(Filter::getName).map(Criteria.where("name.first")::regex).ifPresent(query::addCriteria);
        optFilter.map(Filter::getLastname).map(Criteria.where("name.last")::regex).ifPresent(query::addCriteria);
        optFilter.map(Filter::getEmail).map(Criteria.where("email")::regex).ifPresent(query::addCriteria);
        optFilter.map(Filter::getNationality).map(Criteria.where("nat")::regex).ifPresent(query::addCriteria);

        var page = optFilter.map(Filter::getPage).orElse(0);
        var size = optFilter.map(Filter::getSize).orElse(10);

        long count = mongoTemplate.count(query, UserDataEntity.class);

        query.with(PageRequest.of(page, size));
        List<UserDataEntity> users = mongoTemplate.find(query, UserDataEntity.class);
        return PageableExecutionUtils.getPage(users, PageRequest.of(page, size), () -> count);
    }

    public Optional<UserDataEntity> findByUsernameOrMail(Optional<String> optFilter) {
        Query query = new Query();
        optFilter.map(Criteria.where("login.username")::is).ifPresent(query::addCriteria);
        List<UserDataEntity> users = mongoTemplate.find(query, UserDataEntity.class);
        return users.stream().findFirst();
    }

}
