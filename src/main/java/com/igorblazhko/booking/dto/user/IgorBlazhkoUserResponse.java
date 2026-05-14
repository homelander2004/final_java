package com.igorblazhko.booking.dto.user;

import java.time.LocalDateTime;

public record IgorBlazhkoUserResponse(
        Long id,
        String fullName,
        String email,
        String role,
        boolean enabled,
        LocalDateTime createdAt
) {
}