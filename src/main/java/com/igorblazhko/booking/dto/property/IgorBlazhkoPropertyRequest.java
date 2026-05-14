package com.igorblazhko.booking.dto.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IgorBlazhkoPropertyRequest(
        @NotBlank @Size(max = 150) String name,
        @NotBlank @Size(max = 100) String city,
        @NotBlank @Size(max = 200) String address,
        @Size(max = 2000) String description,
        @NotNull Long adminUserId,
        Long imageFileId
) {
}