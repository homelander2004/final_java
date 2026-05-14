package com.igorblazhko.booking.dto.report;

import java.time.LocalDateTime;

public record IgorBlazhkoBookingReportResponse(
        long activeBookings,
        long completedBookings,
        LocalDateTime generatedAt,
        String generatedBy
) {
}