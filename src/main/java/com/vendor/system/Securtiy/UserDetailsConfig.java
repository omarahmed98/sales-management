package com.vendor.system.Securtiy;

import com.vendor.system.Entity.User;

import com.vendor.system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserDetailsConfig {
    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        log.info("user details service ");
        log.info("load user by username");
        return username -> {
            log.info("load user by username {} {} ",username,repository.findByUsername(username));
            User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new CustomUserDetails(user);
        };
    }
}
