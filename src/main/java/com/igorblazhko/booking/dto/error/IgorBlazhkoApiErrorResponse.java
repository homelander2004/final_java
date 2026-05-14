package com.igorblazhko.booking.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

public record IgorBlazhkoApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        Map<String, String> validationErrors
) {
}