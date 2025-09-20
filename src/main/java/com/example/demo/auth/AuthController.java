package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.BenutzerDtoReq;
import com.example.demo.dto.BenutzerDtoRes;
import com.example.demo.models.Benutzer;
import com.example.demo.models.RefreshToken;
import com.example.demo.services.BenutzerService;
import com.example.demo.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final BenutzerService benutzerService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<BenutzerDtoRes> register(@RequestBody BenutzerDtoReq benutzerDtoReq) {
        System.out.println("register");
        BenutzerDtoRes benutzerDtoRes = benutzerService.save(benutzerDtoReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(benutzerDtoRes);
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLoginName(), authRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLoginName());
        Benutzer benutzer = benutzerService.findByLoginName(authRequest.getLoginName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String accessToken = jwtService.generateAccessToken(userDetails);
        refreshTokenService.deleteByBenutzer(benutzer);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(benutzer);


        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refreshToken");
        AuthResponse authResponse = refreshTokenService.refreshAccessToken(requestToken);
        return ResponseEntity.ok(authResponse);
    }


//    @PostMapping("/refresh")
//    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
//
//        String requestToken = request.get("refreshToken");
//        if (requestToken == null || requestToken.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        return refreshTokenService.findByToken(requestToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getBenutzer)
//                .map(benutzer -> {
//                    String accessToken = jwtService.generateAccessToken(benutzer.getLoginName());
//                    String refreshToken = refreshTokenService.createRefreshToken(benutzer).getToken();
//
//                    return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
//                })
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
//
//    }


}