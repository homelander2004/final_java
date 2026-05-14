package com.igorblazhko.booking.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IgorBlazhkoRegisterRequest(
        @NotBlank @Size(max = 32) String fullName,
        @NotBlank @Email @Size(max = 32) String email,
        @NotBlank @Size(min = 8, max = 20) String password
) {
}