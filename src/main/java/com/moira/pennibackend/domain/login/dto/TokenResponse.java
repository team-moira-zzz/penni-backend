package com.moira.pennibackend.domain.login.dto;

public record TokenResponse(
        String atk,
        String rtk
) {
}
