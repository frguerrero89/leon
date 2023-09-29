package com.ngsoftware.leon.services;

import com.ngsoftware.leon.persistence.entities.UserEntity;
import com.ngsoftware.leon.persistence.repos.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UsersSecurityService implements UserDetailsService {

    private final UserRepo userRepo;

    public UsersSecurityService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepo.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+username));
        log.info("Encontrado usuario: "+ userEntity.toString());
        return User.builder()
                .username(userEntity.getUser())
                .password(userEntity.getPassword())
                .roles("ADMIN")
                .accountLocked(userEntity.isLocked())
                .disabled(userEntity.isDisabled())
                .build();
    }
}
