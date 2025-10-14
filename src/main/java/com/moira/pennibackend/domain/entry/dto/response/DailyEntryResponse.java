package com.moira.pennibackend.domain.entry.dto.response;

public record DailyEntryResponse(
        // 가계부 항목 관련
        String id,
        String type,
        String content,
        String description,
        Long price,
        String method,

        // 카테고리 관련
        Long categoryId,
        String categoryName,

        // 유저 관련
        String userId,
        String userNickname
) {
}
