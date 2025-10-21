package com.moira.pennibackend.domain.fixed.dto.response;

import java.time.OffsetDateTime;

public record FixedExpenseResponse(
        String id,
        String content,
        String description,
        String cycle,
        Integer monthlyDay,
        Integer annualMonth,
        Integer annualDay,
        Integer price,
        String method,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
