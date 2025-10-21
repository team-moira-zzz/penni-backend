package com.moira.pennibackend.domain.fixed.dto.request;

import com.moira.pennibackend.global.entity.enums.FixedExpenseCycle;

import java.time.OffsetDateTime;

public record FixedExpenseAddRequest(
        String content,
        String description,
        String cycle,
        Integer monthlyDay,
        Integer annualMonth,
        Integer annualDay,
        int price
) {
}
