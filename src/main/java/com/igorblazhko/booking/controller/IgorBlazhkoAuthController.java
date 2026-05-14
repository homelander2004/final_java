package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.auth.IgorBlazhkoAuthResponse;
import com.igorblazhko.booking.dto.auth.IgorBlazhkoLoginRequest;
import com.igorblazhko.booking.dto.auth.IgorBlazhkoRegisterRequest;
import com.igorblazhko.booking.service.IgorBlazhkoAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class IgorBlazhkoAuthController {

    private final IgorBlazhkoAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<IgorBlazhkoAuthResponse> register(@Valid @RequestBody IgorBlazhkoRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<IgorBlazhkoAuthResponse> login(@Valid @RequestBody IgorBlazhkoLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}