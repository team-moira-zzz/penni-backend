package com.moira.pennibackend.domain.login.dto.response.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverUserInfoResponse(
        String message,

        @JsonProperty("response")
        NaverResponse response
) {
}
