package com.example.demo.auth;

import com.example.demo.models.Benutzer;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BenutzerService benutzerService;

    public UserDetailsServiceImpl(@Lazy BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer benutzer = benutzerService.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(benutzer.getLoginName())
                .password(benutzer.getPassword())
                .roles(benutzer.getRole().name())  // роль без "ROLE_" префикса Spring Security сам добавит
                .build();
    }
}