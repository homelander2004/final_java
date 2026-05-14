package com.igorblazhko.booking.repository;

import com.igorblazhko.booking.entity.IgorBlazhkoBookingEntity;
import com.igorblazhko.booking.entity.IgorBlazhkoBookingStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IgorBlazhkoBookingRepository extends JpaRepository<IgorBlazhkoBookingEntity, Long> {

    List<IgorBlazhkoBookingEntity> findByClientId(Long clientId);

    long countByStatusAndCheckInDateLessThanEqual(IgorBlazhkoBookingStatus status, LocalDate date);
}