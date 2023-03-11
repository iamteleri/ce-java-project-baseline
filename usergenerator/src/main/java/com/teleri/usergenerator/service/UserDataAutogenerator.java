package com.teleri.usergenerator.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@ConditionalOnProperty("generateusers")
@RequiredArgsConstructor
public class UserDataAutogenerator {

    final UserDataService service;

    @PostConstruct //Initialize 30 users async
    public void initUsers() {
        CompletableFuture.supplyAsync(() ->  {
            for (int i = 0; i < 30; i++) {
                try {
                    service.getAndSendUserData();
                } catch (Exception e) {
                    log.error("Error generating users");
                }
            }
            return null;
        });
    }

}
