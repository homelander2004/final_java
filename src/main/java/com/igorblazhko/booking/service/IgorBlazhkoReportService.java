package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.report.IgorBlazhkoBookingReportResponse;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoReportService {

    private final IgorBlazhkoBookingService bookingService;
    private final IgorBlazhkoBookingAsyncService bookingAsyncService;

    public CompletableFuture<IgorBlazhkoBookingReportResponse> generateBookingSummary(String generatedBy) {
        return bookingAsyncService.buildBookingSummary(
                bookingService.countActiveBookings(),
                bookingService.countCompletedBookings(),
                generatedBy
        );
    }
}