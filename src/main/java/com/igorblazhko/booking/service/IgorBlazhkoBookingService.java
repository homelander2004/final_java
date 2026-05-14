package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.booking.IgorBlazhkoBookingRequest;
import com.igorblazhko.booking.dto.booking.IgorBlazhkoBookingResponse;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingEntity;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingStatus;
import com.igorblazhko.booking.exception.IgorBlazhkoBadRequestException;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoBookingMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoBookingRepository;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IgorBlazhkoBookingService {

    private final IgorBlazhkoBookingRepository bookingRepository;
    private final IgorBlazhkoRoomService roomService;
    private final IgorBlazhkoUserService userService;
    private final IgorBlazhkoBookingMapper bookingMapper;
    private final IgorBlazhkoBookingAsyncService bookingAsyncService;

    public List<IgorBlazhkoBookingResponse> getAllBookings(Long clientId) {
        List<IgorBlazhkoBookingEntity> bookings = clientId == null
                ? bookingRepository.findAll()
                : bookingRepository.findByClientId(clientId);
        return bookings.stream().map(bookingMapper::toResponse).toList();
    }

    public IgorBlazhkoBookingResponse getBookingById(Long id) {
        return bookingMapper.toResponse(findBookingEntity(id));
    }

    @Transactional
    public IgorBlazhkoBookingResponse createBooking(IgorBlazhkoBookingRequest request) {
        log.debug("Creating booking: roomId={}, clientId={}, checkIn={}, checkOut={}",
                request.roomId(), request.clientId(), request.checkInDate(), request.checkOutDate());
        validateDates(request);
        var room = roomService.findRoomEntity(request.roomId());
        if (!room.isAvailable()) {
            log.warn("Booking rejected because room is unavailable: roomId={}", request.roomId());
            throw new IgorBlazhkoBadRequestException("Selected room is not available");
        }

        IgorBlazhkoBookingEntity booking = new IgorBlazhkoBookingEntity();
        booking.setRoom(room);
        booking.setClient(userService.findUserEntity(request.clientId()));
        booking.setCheckInDate(request.checkInDate());
        booking.setCheckOutDate(request.checkOutDate());
        booking.setTotalPrice(calculateTotalPrice(room.getPricePerNight(), request));
        booking.setStatus(IgorBlazhkoBookingStatus.PENDING);
        room.setAvailable(false);
        IgorBlazhkoBookingEntity savedBooking = bookingRepository.save(booking);
        log.info("Booking created: bookingId={}, roomId={}, clientId={}, totalPrice={}",
            savedBooking.getId(), savedBooking.getRoom().getId(), savedBooking.getClient().getId(), savedBooking.getTotalPrice());
        bookingAsyncService.sendBookingCreatedNotification(savedBooking.getId(), savedBooking.getClient().getEmail());
        return bookingMapper.toResponse(savedBooking);
    }

    @Transactional
    public IgorBlazhkoBookingResponse updateBooking(Long id, IgorBlazhkoBookingRequest request) {
        validateDates(request);
        IgorBlazhkoBookingEntity booking = findBookingEntity(id);
        var room = roomService.findRoomEntity(request.roomId());
        booking.setRoom(room);
        booking.setClient(userService.findUserEntity(request.clientId()));
        booking.setCheckInDate(request.checkInDate());
        booking.setCheckOutDate(request.checkOutDate());
        booking.setTotalPrice(calculateTotalPrice(room.getPricePerNight(), request));
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public IgorBlazhkoBookingResponse changeStatus(Long id, IgorBlazhkoBookingStatus status) {
        IgorBlazhkoBookingEntity booking = findBookingEntity(id);
        log.info("Changing booking status: bookingId={}, oldStatus={}, newStatus={}", id, booking.getStatus(), status);
        booking.setStatus(status);
        if (status == IgorBlazhkoBookingStatus.CANCELLED || status == IgorBlazhkoBookingStatus.COMPLETED) {
            booking.getRoom().setAvailable(true);
            log.debug("Room availability restored after booking status change: bookingId={}, roomId={}", id, booking.getRoom().getId());
        }
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public void deleteBooking(Long id) {
        IgorBlazhkoBookingEntity booking = findBookingEntity(id);
        booking.getRoom().setAvailable(true);
        log.warn("Deleting booking: bookingId={}, roomId={}", id, booking.getRoom().getId());
        bookingRepository.delete(booking);
    }

    public IgorBlazhkoBookingEntity findBookingEntity(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("Booking not found with id: " + id));
    }

    public long countActiveBookings() {
        return bookingRepository.countByStatusAndCheckInDateLessThanEqual(IgorBlazhkoBookingStatus.CONFIRMED, java.time.LocalDate.now());
    }

    public long countCompletedBookings() {
        return bookingRepository.findAll().stream().filter(booking -> booking.getStatus() == IgorBlazhkoBookingStatus.COMPLETED).count();
    }

    private void validateDates(IgorBlazhkoBookingRequest request) {
        if (!request.checkOutDate().isAfter(request.checkInDate())) {
            log.warn("Invalid booking dates: roomId={}, clientId={}, checkIn={}, checkOut={}",
                    request.roomId(), request.clientId(), request.checkInDate(), request.checkOutDate());
            throw new IgorBlazhkoBadRequestException("Check-out date must be after check-in date");
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, IgorBlazhkoBookingRequest request) {
        long nights = ChronoUnit.DAYS.between(request.checkInDate(), request.checkOutDate());
        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }
}