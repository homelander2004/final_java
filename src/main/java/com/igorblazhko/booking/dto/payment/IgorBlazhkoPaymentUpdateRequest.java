package com.igorblazhko.booking.dto.payment;

import com.igorblazhko.booking.entity.IgorBlazhkoPaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IgorBlazhkoPaymentUpdateRequest(
        @NotNull IgorBlazhkoPaymentStatus status,
        @NotBlank String paymentMethod
) {
}