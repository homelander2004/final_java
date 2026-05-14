package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.booking.IgorBlazhkoBookingRequest;
import com.igorblazhko.booking.dto.booking.IgorBlazhkoBookingResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingStatus;
import com.igorblazhko.booking.service.IgorBlazhkoBookingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class IgorBlazhkoBookingController {

    private final IgorBlazhkoBookingService bookingService;

    @GetMapping
    public ResponseEntity<List<IgorBlazhkoBookingResponse>> getAllBookings(@RequestParam(required = false) Long clientId) {
        return ResponseEntity.ok(bookingService.getAllBookings(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgorBlazhkoBookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<IgorBlazhkoBookingResponse> createBooking(@Valid @RequestBody IgorBlazhkoBookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgorBlazhkoBookingResponse> updateBooking(@PathVariable Long id,
                                                                    @Valid @RequestBody IgorBlazhkoBookingRequest request) {
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<IgorBlazhkoBookingResponse> changeStatus(@PathVariable Long id,
                                                                   @RequestParam IgorBlazhkoBookingStatus status) {
        return ResponseEntity.ok(bookingService.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}