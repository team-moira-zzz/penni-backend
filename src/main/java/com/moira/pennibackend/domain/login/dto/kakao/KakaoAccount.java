package com.moira.pennibackend.domain.login.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAccount(
        String email,

        @JsonProperty("profile")
        KakaoProfile profile
) {
}
