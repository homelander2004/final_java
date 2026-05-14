package com.igorblazhko.booking.dto.booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IgorBlazhkoBookingResponse(
        Long id,
        Long roomId,
        String roomNumber,
        Long clientId,
        String clientName,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalPrice,
        String status,
        LocalDateTime createdAt,
        Long paymentId
) {
}