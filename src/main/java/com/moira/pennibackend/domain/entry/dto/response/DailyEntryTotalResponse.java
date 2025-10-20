package com.moira.pennibackend.domain.entry.dto.response;

public record DailyEntryTotalResponse(
        Long totalIncome,
        Long totalExpense
) {
}
