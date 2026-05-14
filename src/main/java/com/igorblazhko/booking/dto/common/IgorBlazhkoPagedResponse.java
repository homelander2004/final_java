package com.igorblazhko.booking.dto.common;

import java.util.List;
import lombok.Builder;

@Builder
public record IgorBlazhkoPagedResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}