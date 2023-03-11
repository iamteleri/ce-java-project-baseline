package com.teleri.userapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teleri.userapi.dto.*;
import com.teleri.userapi.mapper.DataMapper;
import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.security.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminDataInitializer {

    final UserDataService service;
    final DataMapper mapper;
    final PasswordEncoder encoder;

    @PostConstruct
    public void init() throws IOException {
        initAdmin();
    }

    public void initAdmin() throws IOException {
        UserDataEntity user = UserDataEntity.builder()
                .gender("female")
                .name(Name.builder()
                        .title("Mrs")
                        .first("Admin")
                        .last("Admin")
                        .build())
                .location(Location.builder()
                        .street(Street.builder()
                                .number(2)
                                .name("Calle del Desarrollo")
                                .build())
                        .city("Jerez de la Frontera")
                        .state("Andalucia")
                        .country("Spain")
                        .postcode("11408")
                        .coordinates(Coordinates.builder()
                                .latitude("-50.3559")
                                .longitude("88.9829")
                                .build())
                        .timezone(Timezone.builder()
                                .offset("+3:00")
                                .description("Baghdad, Riyadh, Moscow, St. Petersburg")
                                .build())
                        .build())
                .email("admin@example.com")
                .login(Login.builder()
                        .uuid("d678d440-a1db-415d-a93d-b7389147795a")
                        .username("admin")
                        .password(encoder.encode("admin"))
                        .salt("e1Rgv1QJ")
                        .md5("fef3e007b367a954c92dae439d03c7bf")
                        .sha1("127d75ff6ed6b9644084e37de3250affbdeaf08b")
                        .sha256("9dfe064a42466e5a613e7223478a05bc8d5bb256a69b1644724e088a8cdcc3a5")
                        .build())
                .dob(Dob.builder()
                        .date("1955-08-22T05:01:13.310Z")
                        .age(67)
                        .build())
                .registered(Registered.builder()
                        .date("2013-03-03T20:46:14.820Z")
                        .age(10)
                        .build())
                .phone("0134-4536291")
                .cell("0178-4194670")
                .picture(Picture.builder()
                        .large("https://randomuser.me/api/portraits/women/55.jpg")
                        .medium("https://randomuser.me/api/portraits/med/women/55.jpg")
                        .thumbnail("https://randomuser.me/api/portraits/thumb/women/55.jpg")
                        .build())
                .nat("ES")
                .roles(Arrays.asList(RoleEnum.ROLE_ADMIN))
                .build();


        log.info("Saving admin user: {}", user);
        service.save(user);
    }

}
