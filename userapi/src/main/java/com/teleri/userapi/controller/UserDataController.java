package com.teleri.userapi.controller;

import com.teleri.userapi.dto.Result;
import com.teleri.userapi.dto.UserData;
import com.teleri.userapi.mapper.DataMapper;
import com.teleri.userapi.model.Filter;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "Users")
public class UserDataController {

    final UserDataService service;
    final DataMapper mapper;

    @CrossOrigin
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves a User by its id")
    public ResponseEntity<Result> getById(@PathVariable("id") String id) {
        var optDto = Optional.ofNullable(id).map(service::getUser).map(mapper::toDto);
        return optDto.isPresent()
                ? ResponseEntity.ok(optDto.get())
                : ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping
    @ApiOperation(value = "Retrieves users based on a criteria set")
    public ResponseEntity<Page<Result>> getByCriteria(
            @RequestParam(name = "name", required=false) String name,
            @RequestParam(name = "lastname", required=false) String lastname,
            @RequestParam(name = "email", required=false) String email,
            @RequestParam(name = "nat", required=false) String nationality,
            @RequestParam(name = "page", defaultValue = "0", required=false) String page,
            @RequestParam(name = "size", defaultValue = "10", required=false) String size) {
        Filter filter = Filter.builder().name(name).lastname(lastname).email(email)
                .nationality(nationality).page(Integer.valueOf(page)).size(Integer.valueOf(size)).build();
        Page<UserDataEntity> filtered = service.getFiltered(filter);
        List<Result> collectedData = filtered.getContent().stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity
                .ok(PageableExecutionUtils.getPage(collectedData,
                        PageRequest.of(Integer.valueOf(page), Integer.valueOf(size)),
                        () -> filtered.getTotalElements()));
    }

    @CrossOrigin
    @PostMapping
    @ApiOperation(value = "Creates a user")
    public ResponseEntity<Result> create(@RequestBody Result result) {
        var optInsert = Optional.ofNullable(result).map(mapper::toEntity).map(service::save).map(mapper::toDto);
        return optInsert.isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).body(optInsert.get())
                : ResponseEntity.badRequest().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a user based on its id")
    public ResponseEntity<Result> update(@RequestBody Result result, @PathVariable("id") String id) {
        result.setId(id);
        var existsUser = Optional.ofNullable(id).filter(service::existsUser).isPresent();
        var optEntity = Optional.ofNullable(result).map(mapper::toEntity)
                .map(service::save).map(mapper::toDto);
        if (existsUser) {
            return optEntity.isPresent()
                    ? ResponseEntity.ok(optEntity.get())
                    :ResponseEntity.badRequest().build();
        } else {
        return optEntity.isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).body(optEntity.get())
                :ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Removes a user based on its id")
    public ResponseEntity delete(@PathVariable("id") String id) {
        Optional.ofNullable(id).ifPresent(service::delete);
        return ResponseEntity.noContent().build();
    }

}
