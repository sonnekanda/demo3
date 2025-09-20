package com.example.demo.repositories;

import com.example.demo.models.Benutzer;
import com.example.demo.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Long deleteByBenutzer(Benutzer benutzer);
}
