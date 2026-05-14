package com.igorblazhko.booking.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IgorBlazhkoPaymentRequest(
        @NotNull Long bookingId,
        @NotBlank String paymentMethod
) {
}