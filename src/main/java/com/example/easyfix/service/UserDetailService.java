package com.example.easyfix.service;

import com.example.easyfix.model.User;
import com.example.easyfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        log.info("came to loadUserByUsername");
        User user = userRepo.findByEmail(email);

        if (user == null) {
            log.info("User Not Found. Invalid User");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserDetail(user);
    }
}