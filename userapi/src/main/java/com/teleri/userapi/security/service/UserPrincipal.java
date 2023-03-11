package com.teleri.userapi.security.service;

import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.security.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private String username;
    private String email;
    private String password;
    private String id;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal build(UserDataEntity userEntity) {
        Collection<GrantedAuthority> authorities =
                userEntity.getRoles().stream().map(RoleEnum::name).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return  UserPrincipal.builder().username(userEntity.getLogin().getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getLogin().getPassword())
                .authorities(authorities)
                .id(userEntity.getId())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }
}
