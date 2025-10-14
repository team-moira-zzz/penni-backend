package com.moira.pennibackend.domain.entry.dto.request;

import java.time.LocalDate;

public record AccountBookEntryUpdateRequest(
        Long categoryId,
        String content,
        String description,
        Integer price,
        LocalDate date,
        String type,
        String method
) {
}
