package com.igorblazhko.booking.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record IgorBlazhkoPaymentResponse(
        Long id,
        Long bookingId,
        BigDecimal amount,
        String status,
        String paymentMethod,
        LocalDateTime createdAt,
        LocalDateTime paidAt
) {
}