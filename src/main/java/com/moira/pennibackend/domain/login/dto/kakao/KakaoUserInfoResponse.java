package com.moira.pennibackend.domain.login.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
        Long id,

        @JsonProperty("kakao_account")
        KakaoAccount account
) {
}
