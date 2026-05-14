package com.igorblazhko.booking.dto.room;

import java.math.BigDecimal;

public record IgorBlazhkoRoomResponse(
        Long id,
        String roomNumber,
        String roomType,
        BigDecimal pricePerNight,
        Integer capacity,
        boolean available,
        Long propertyId,
        String propertyName,
        String city
) {
}