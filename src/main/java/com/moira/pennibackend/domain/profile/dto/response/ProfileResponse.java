package com.moira.pennibackend.domain.profile.dto.response;

import com.moira.pennibackend.global.entity.enums.UserType;

import java.time.LocalDateTime;

public record ProfileResponse(
        String id,
        UserType type,
        String email,
        String name,
        String nickname,
        String phone,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
}
