package com.moira.pennibackend.domain.fixed.dto.request;

public record FixedExpenseUpdateRequest(
        String content,
        String description,
        String cycle,
        Integer monthlyDay,
        Integer annualMonth,
        Integer annualDay,
        int price
) {
}
