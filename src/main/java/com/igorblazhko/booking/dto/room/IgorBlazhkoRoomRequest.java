package com.igorblazhko.booking.dto.room;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record IgorBlazhkoRoomRequest(
        @NotBlank @Size(max = 30) String roomNumber,
        @NotBlank @Size(max = 100) String roomType,
        @NotNull @DecimalMin("0.0") BigDecimal pricePerNight,
        @NotNull @Min(1) Integer capacity,
        @NotNull Boolean available,
        @NotNull Long propertyId
) {
}