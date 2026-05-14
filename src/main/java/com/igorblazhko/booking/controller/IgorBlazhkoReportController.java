package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.report.IgorBlazhkoBookingReportResponse;
import com.igorblazhko.booking.security.IgorBlazhkoUserPrincipal;
import com.igorblazhko.booking.service.IgorBlazhkoReportService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class IgorBlazhkoReportController {

    private final IgorBlazhkoReportService reportService;

    @GetMapping("/bookings/summary")
    public CompletableFuture<ResponseEntity<IgorBlazhkoBookingReportResponse>> getBookingSummary(
            @AuthenticationPrincipal IgorBlazhkoUserPrincipal principal) {
        String generatedBy = principal == null ? "anonymous" : principal.getEmail();
        return reportService.generateBookingSummary(generatedBy).thenApply(ResponseEntity::ok);
    }
}