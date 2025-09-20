package com.example.demo.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final BenutzerService benutzerService;

    public AuthController(BenutzerService benutzerService) {
        this.benutzerService = benutzerService;
    }

    @PostMapping("/register")
    public ResponseEntity<BenutzerDtoRes> register(@RequestBody BenutzerDtoReq benutzerDtoReq) {
        System.out.println("register");
        BenutzerDtoRes benutzerDtoRes = benutzerService.save(benutzerDtoReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(benutzerDtoRes);
    }

}