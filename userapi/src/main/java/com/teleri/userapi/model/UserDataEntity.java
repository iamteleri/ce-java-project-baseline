package com.teleri.userapi.model;

import com.teleri.userapi.dto.*;
import com.teleri.userapi.security.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataEntity {
    @Id
    private String id;
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private Login login;
    private Dob dob;
    private Registered registered;
    private String phone;
    private String cell;
    private Picture picture;
    private String nat;
    private List<RoleEnum> roles;
}

