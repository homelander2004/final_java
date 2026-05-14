package com.igorblazhko.booking.dto.booking;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record IgorBlazhkoBookingRequest(
        @NotNull Long roomId,
        @NotNull Long clientId,
        @NotNull @FutureOrPresent LocalDate checkInDate,
        @NotNull @FutureOrPresent LocalDate checkOutDate
) {
}