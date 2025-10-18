package com.moira.pennibackend.domain.login.dto.response.naver;

public record NaverResponse(
        String id,

        String nickname,

        String name,

        String email,

        String mobile
) {
}