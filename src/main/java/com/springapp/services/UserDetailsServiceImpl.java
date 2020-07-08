package com.springapp.services;

import com.springapp.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    //сервис для WebSecurityConfig

    //-------------------------------------------------------------
    @Autowired
    UserRepoService userRepoService;

    //-------------------------------------------------------------
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepoService.findByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        userEntity.setOnline(true); //TODO: активность пользователя

        return userEntity;
    }
}
