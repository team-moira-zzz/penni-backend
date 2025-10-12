package com.moira.pennibackend.domain.login.dto.response;

public record TokenResponse(
        String atk,
        String rtk
) {
}
