package com.example.demo.services;

import com.example.demo.config.JwtService;
import com.example.demo.dto.AuthResponse;
import com.example.demo.models.Benutzer;
import com.example.demo.models.RefreshToken;
import com.example.demo.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BenutzerService benutzerService;
    private final JwtService jwtService;

    @Value("${jwt.refresh.lifetime}")
    private long refreshTokenMinutes;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, BenutzerService benutzerService, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.benutzerService = benutzerService;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(Benutzer benutzer) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setBenutzer(benutzer);
        refreshToken.setExpiryDate(Instant.now().plus(refreshTokenMinutes, ChronoUnit.MINUTES));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again.");
        }
        return token;
    }

//    public Long deleteByBenutzerId(Long benutzerId) {
//        Benutzer benutzer = benutzerService.findById(benutzerId)
//                .orElseThrow(() -> new RuntimeException("Benutzer not found"));
//        return refreshTokenRepository.deleteByBenutzer(benutzer);
//    }

    public void deleteByBenutzer(Benutzer benutzer) {
        refreshTokenRepository.deleteByBenutzer(benutzer);
    }


    public AuthResponse refreshAccessToken(String requestToken) {

        if (requestToken == null || requestToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is invalid or empty");
        }

        RefreshToken refreshToken = findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        verifyExpiration(refreshToken);

        Benutzer benutzer = refreshToken.getBenutzer();

        String accessToken = jwtService.generateAccessToken(benutzer.getLoginName());
        RefreshToken newRefreshToken = createRefreshToken(benutzer);

        refreshTokenRepository.delete(refreshToken);

        return new AuthResponse(accessToken, newRefreshToken.getToken());

    }
}
