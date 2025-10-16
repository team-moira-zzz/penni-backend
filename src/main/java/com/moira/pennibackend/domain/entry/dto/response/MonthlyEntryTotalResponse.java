package com.moira.pennibackend.domain.entry.dto.response;

public record MonthlyEntryTotalResponse(
        Long totalIncome,
        Long totalExpense
) {
}
