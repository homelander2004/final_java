package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentRequest;
import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentResponse;
import com.igorblazhko.booking.dto.payment.IgorBlazhkoPaymentUpdateRequest;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingStatus;
import com.igorblazhko.booking.entity.IgorBlazhkoPaymentEntity;
import com.igorblazhko.booking.entity.IgorBlazhkoPaymentStatus;
import com.igorblazhko.booking.exception.IgorBlazhkoConflictException;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoPaymentMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoPaymentRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IgorBlazhkoPaymentService {

    private final IgorBlazhkoPaymentRepository paymentRepository;
    private final IgorBlazhkoBookingService bookingService;
    private final IgorBlazhkoPaymentMapper paymentMapper;
    private final IgorBlazhkoBookingAsyncService bookingAsyncService;

    public List<IgorBlazhkoPaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::toResponse).toList();
    }

    public IgorBlazhkoPaymentResponse getPaymentById(Long id) {
        return paymentMapper.toResponse(findPaymentEntity(id));
    }

    @Transactional
    public IgorBlazhkoPaymentResponse createPayment(IgorBlazhkoPaymentRequest request) {
        log.debug("Creating payment for bookingId={}, paymentMethod={}", request.bookingId(), request.paymentMethod());
        var booking = bookingService.findBookingEntity(request.bookingId());
        if (booking.getPayment() != null) {
            log.warn("Duplicate payment attempt for bookingId={}", request.bookingId());
            throw new IgorBlazhkoConflictException("Payment for this booking already exists");
        }
        IgorBlazhkoPaymentEntity payment = new IgorBlazhkoPaymentEntity();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalPrice());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(IgorBlazhkoPaymentStatus.PENDING);
        IgorBlazhkoPaymentEntity savedPayment = paymentRepository.save(payment);
        log.info("Payment created: paymentId={}, bookingId={}, amount={}, status={}",
                savedPayment.getId(), booking.getId(), savedPayment.getAmount(), savedPayment.getStatus());
        return paymentMapper.toResponse(savedPayment);
    }

    @Transactional
    public IgorBlazhkoPaymentResponse updatePayment(Long id, IgorBlazhkoPaymentUpdateRequest request) {
        log.debug("Updating payment: paymentId={}, newStatus={}, paymentMethod={}", id, request.status(), request.paymentMethod());
        IgorBlazhkoPaymentEntity payment = findPaymentEntity(id);
        payment.setPaymentMethod(request.paymentMethod());
        payment.setStatus(request.status());
        if (request.status() == IgorBlazhkoPaymentStatus.PAID) {
            payment.setPaidAt(LocalDateTime.now());
            payment.getBooking().setStatus(IgorBlazhkoBookingStatus.CONFIRMED);
            log.info("Payment confirmed: paymentId={}, bookingId={} moved to CONFIRMED", payment.getId(), payment.getBooking().getId());
            bookingAsyncService.sendPaymentReceipt(payment.getId(), payment.getBooking().getClient().getEmail());
        } else if (request.status() == IgorBlazhkoPaymentStatus.REFUNDED) {
            log.warn("Payment refunded: paymentId={}, bookingId={}", payment.getId(), payment.getBooking().getId());
        }
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Transactional
    public void deletePayment(Long id) {
        log.warn("Deleting payment: paymentId={}", id);
        paymentRepository.delete(findPaymentEntity(id));
    }

    public IgorBlazhkoPaymentEntity findPaymentEntity(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("Payment not found with id: " + id));
    }
}