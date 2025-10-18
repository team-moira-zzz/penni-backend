package com.moira.pennibackend.domain.group.dto.response;

import java.time.LocalDateTime;

public record GroupResponse(
        // 그룹 관련
        String groupId,
        String name,
        String description,
        String code,
        LocalDateTime createdAt,
        
        // 유저 관련
        String userId,
        String userNickname
) {
}
