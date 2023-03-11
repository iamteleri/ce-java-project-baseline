package com.teleri.usergenerator.controller;

import com.teleri.usergenerator.service.UserDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class GeneratorController {

    final UserDataService service;

    @CrossOrigin
    @PostMapping
    public ResponseEntity generateUser() {
        try {
            service.getAndSendUserData();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
