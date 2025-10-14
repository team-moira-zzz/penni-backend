package com.moira.pennibackend.domain.entry.dto.response;

import java.time.LocalDate;

public record DailyEntryTotalResponse(
        LocalDate date,
        Long totalIncome,
        Long totalExpense
) {
}
