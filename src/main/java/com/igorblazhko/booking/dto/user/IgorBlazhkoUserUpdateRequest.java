package com.igorblazhko.booking.dto.user;

import com.igorblazhko.booking.entity.IgorBlazhkoRoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IgorBlazhkoUserUpdateRequest(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email @Size(max = 120) String email,
        @NotNull Boolean enabled,
        @NotNull IgorBlazhkoRoleName role
) {
}