package com.example.demo.auth;

import com.example.demo.models.Benutzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final BenutzerService benutzerService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(BenutzerService benutzerService, PasswordEncoder passwordEncoder) {
        this.benutzerService = benutzerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Benutzer benutzer) {
        System.out.println("register");
        // шифрование пароля
        benutzer.setPassword(passwordEncoder.encode(benutzer.getPassword()));
        benutzerService.save(benutzer);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
    }

    // Для базовой аутентификации логин и пароль передаются автоматически HTTP Basic
}