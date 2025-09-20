package com.example.demo.auth;

import com.example.demo.models.Benutzer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BenutzerService {

    private final BenutzerRepository benutzerRepository;

    public BenutzerService(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
    }

    public Benutzer save(Benutzer benutzer) {
        // Можно добавить проверку уникальности loginName
        return benutzerRepository.save(benutzer);
    }

    public Optional<Benutzer> findByLoginName(String loginName) {
        return benutzerRepository.findByLoginName(loginName);
    }
}