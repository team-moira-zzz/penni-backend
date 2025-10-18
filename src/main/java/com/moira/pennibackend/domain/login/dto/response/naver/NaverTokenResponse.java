package com.moira.pennibackend.domain.login.dto.response.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverTokenResponse(
        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("expires_in")
        Integer expiresIn,

        @JsonProperty("error")
        String errorCode,

        @JsonProperty("errorDescription")
        String errorDescription
) {
}