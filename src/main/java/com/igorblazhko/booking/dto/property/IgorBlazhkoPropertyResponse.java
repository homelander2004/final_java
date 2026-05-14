package com.igorblazhko.booking.dto.property;

public record IgorBlazhkoPropertyResponse(
        Long id,
        String name,
        String city,
        String address,
        String description,
        Long adminUserId,
        String adminUserName,
        Long imageFileId
) {
}