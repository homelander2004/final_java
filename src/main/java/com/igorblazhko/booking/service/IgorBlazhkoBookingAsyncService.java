package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.report.IgorBlazhkoBookingReportResponse;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IgorBlazhkoBookingAsyncService {

    @Async("igorBlazhkoTaskExecutor")
    public CompletableFuture<Void> sendBookingCreatedNotification(Long bookingId, String email) {
        log.info("Async booking notification started for bookingId={}, email={}", bookingId, email);
        log.info("Async booking notification finished for bookingId={}", bookingId);
        return CompletableFuture.completedFuture(null);
    }

    @Async("igorBlazhkoTaskExecutor")
    public CompletableFuture<Void> sendPaymentReceipt(Long paymentId, String email) {
        log.info("Async payment receipt started for paymentId={}, email={}", paymentId, email);
        log.info("Async payment receipt finished for paymentId={}", paymentId);
        return CompletableFuture.completedFuture(null);
    }

    @Async("igorBlazhkoTaskExecutor")
    public CompletableFuture<IgorBlazhkoBookingReportResponse> buildBookingSummary(long activeBookings,
                                                                                    long completedBookings,
                                                                                    String generatedBy) {
        log.info("Async report generation started by {}", generatedBy);
        IgorBlazhkoBookingReportResponse response = new IgorBlazhkoBookingReportResponse(
                activeBookings,
                completedBookings,
                LocalDateTime.now(),
                generatedBy
        );
        log.info("Async report generation finished by {}", generatedBy);
        return CompletableFuture.completedFuture(response);
    }
}