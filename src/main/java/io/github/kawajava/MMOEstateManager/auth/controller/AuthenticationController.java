package io.github.kawajava.MMOEstateManager.auth.controller;

import io.github.kawajava.MMOEstateManager.auth.model.AuthenticationRequest;
import io.github.kawajava.MMOEstateManager.auth.model.AuthenticationResponse;
import io.github.kawajava.MMOEstateManager.auth.model.RegisterRequest;
import io.github.kawajava.MMOEstateManager.auth.service.AuthenticationService;
import io.github.kawajava.MMOEstateManager.auth.service.JwtService;
import io.github.kawajava.MMOEstateManager.auth.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        var token = authHeader.substring(7);
        tokenBlacklistService.addToBlacklist(token);

        return ResponseEntity.ok("User logged out successfully");
    }
}
