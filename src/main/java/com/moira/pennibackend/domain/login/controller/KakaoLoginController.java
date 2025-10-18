package com.moira.pennibackend.domain.login.controller;

import com.moira.pennibackend.domain.login.dto.request.KakaoCodeRequest;
import com.moira.pennibackend.domain.login.dto.response.AccessTokenResponse;
import com.moira.pennibackend.domain.login.dto.response.TokenResponse;
import com.moira.pennibackend.domain.login.service.KakaoLoginService;
import com.moira.pennibackend.global.utility.CookieHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.moira.pennibackend.global.constant.PenniConstant.KAKAO_AUTH_URL;

@Controller
@RequiredArgsConstructor
public class KakaoLoginController {
    private final CookieHandler cookieHandler;
    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @GetMapping("/api/login/kakao/redirect")
    public String redirect() {
        // [1] 리다이렉트
        String redirectStringFormat = "%s?response_type=code&client_id=%s&redirect_uri=%s";
        String redirectString = redirectStringFormat.formatted(KAKAO_AUTH_URL, kakaoClientId, kakaoRedirectUri);

        return "redirect:" + redirectString;
    }

    @PostMapping("/api/login/kakao")
    ResponseEntity<AccessTokenResponse> login(
            @RequestBody KakaoCodeRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        // [1] IP 추출
        String ipAddress = httpServletRequest.getRemoteAddr();

        // [2] 로그인 성공 후 atk, rtk 반환
        TokenResponse tokens = kakaoLoginService.login(request.code(), ipAddress);
        AccessTokenResponse atk = new AccessTokenResponse(tokens.atk());

        // [3] rtk는 쿠키에 넣어준다.
        cookieHandler.putRtkInCookie(httpServletResponse, tokens.rtk());

        // [4] atk는 요청 본문으로 반환한다.
        return ResponseEntity.ok().body(atk);
    }
}
