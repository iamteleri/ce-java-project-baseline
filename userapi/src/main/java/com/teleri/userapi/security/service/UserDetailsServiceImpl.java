package com.teleri.userapi.security.service;

import com.teleri.userapi.model.UserDataEntity;
import com.teleri.userapi.repository.CustomUserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    final CustomUserDataRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDataEntity> userEntity = userEntityRepository.findByUsernameOrMail(Optional.of(username));
        if(!userEntity.isPresent()){
            throw new UsernameNotFoundException("not exists");
        }
        return UserPrincipal.build(userEntity.get());
    }
}
