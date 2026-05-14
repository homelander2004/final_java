package com.igorblazhko.booking.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record IgorBlazhkoLoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}