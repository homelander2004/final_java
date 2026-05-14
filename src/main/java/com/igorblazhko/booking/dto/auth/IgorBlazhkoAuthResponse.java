package com.igorblazhko.booking.dto.auth;

public record IgorBlazhkoAuthResponse(
        String token,
        String tokenType,
        Long userId,
        String fullName,
        String email,
        String role
) {
}