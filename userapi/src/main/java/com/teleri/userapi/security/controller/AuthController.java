package com.teleri.userapi.security.controller;

import com.teleri.userapi.security.dto.JwtTokenDto;
import com.teleri.userapi.security.dto.LoginUserDto;
import com.teleri.userapi.security.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserEntityService userEntityService;

    @PostMapping
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginUserDto dto)  {
        return ResponseEntity.ok(userEntityService.login(dto));
    }
}
