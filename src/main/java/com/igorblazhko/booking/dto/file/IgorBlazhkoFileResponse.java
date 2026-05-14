package com.igorblazhko.booking.dto.file;

import java.time.LocalDateTime;

public record IgorBlazhkoFileResponse(
        Long id,
        String originalFileName,
        String contentType,
        long size,
        LocalDateTime uploadedAt
) {
}