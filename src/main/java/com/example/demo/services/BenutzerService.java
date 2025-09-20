package com.example.demo.services;

import com.example.demo.dto.BenutzerDtoReq;
import com.example.demo.dto.BenutzerDtoRes;
import com.example.demo.mappers.BenutzerMapper;
import com.example.demo.models.Benutzer;
import com.example.demo.repositories.BenutzerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BenutzerService {

    private final BenutzerRepository benutzerRepository;
    private final BenutzerMapper benutzerMapper;
    private final PasswordEncoder passwordEncoder;

    public BenutzerDtoRes save(BenutzerDtoReq benutzerDtoReq) {

        Benutzer benutzer = benutzerMapper.toEntity(benutzerDtoReq);
        benutzer.setPassword(passwordEncoder.encode(benutzer.getPassword()));
        return benutzerMapper.toDto(benutzerRepository.save(benutzer));
    }

    public Optional<Benutzer> findByLoginName(String loginName) {
        return benutzerRepository.findByLoginName(loginName);
    }

    public Optional<Benutzer> findById(Long benutzerId) {
        return benutzerRepository.findById(benutzerId);

    }
}