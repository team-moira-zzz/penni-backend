package com.moira.pennibackend.domain.entry.dto.response;

public record DailyEntryResponse(
        String id,
        String type,
        String content,
        String description,
        Long price,

        Long categoryId,
        String categoryName,

        String userId,
        String userNickname
) {
}
