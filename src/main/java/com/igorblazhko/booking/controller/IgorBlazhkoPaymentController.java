package com.igorblazhko.booking.controller;

import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentRequest;
import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentResponse;
import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentUpdateRequest;
import com.igorblazhko.booking.service.IgorBlazhkoPaymentService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class IgorBlazhkoPaymentController {

    private final IgorBlazhkoPaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<IgorBlazhkoPaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IgorBlazhkoPaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<IgorBlazhkoPaymentResponse> createPayment(@Valid @RequestBody IgorBlazhkoPaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgorBlazhkoPaymentResponse> updatePayment(@PathVariable Long id,
                                                                    @Valid @RequestBody IgorBlazhkoPaymentUpdateRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}