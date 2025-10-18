package com.moira.pennibackend.domain.login.controller;

import com.moira.pennibackend.domain.login.dto.request.NaverCodeRequest;
import com.moira.pennibackend.domain.login.dto.response.AccessTokenResponse;
import com.moira.pennibackend.domain.login.dto.response.TokenResponse;
import com.moira.pennibackend.domain.login.service.NaverLoginService;
import com.moira.pennibackend.domain.login.service.NaverLoginStateService;
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

import java.util.UUID;

import static com.moira.pennibackend.global.constant.PenniConstant.NAVER_AUTH_URL;

@Controller
@RequiredArgsConstructor
public class NaverLoginController {
    private final CookieHandler cookieHandler;
    private final NaverLoginService naverLoginService;
    private final NaverLoginStateService naverLoginStateService;

    @Value("${naver.client_id}")
    private String naverClientId;

    @Value("${naver.redirect_uri}")
    private String naverRedirectUri;

    @GetMapping("/api/login/naver/redirect")
    public String redirect() {
        // [1] state 생성 후 Redis에 저장
        String state = UUID.randomUUID().toString();
        naverLoginStateService.put(state);

        // [2] 리다이렉트
        String redirectStringFormat = "%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s";
        String redirectString = redirectStringFormat.formatted(NAVER_AUTH_URL, naverClientId, naverRedirectUri, state);

        return "redirect:" + redirectString;
    }

    @PostMapping("/api/login/naver")
    ResponseEntity<AccessTokenResponse> login(
            @RequestBody NaverCodeRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        // [1] IP 추출
        String ipAddress = httpServletRequest.getRemoteAddr();

        // [2] 로그인 성공 후 atk, rtk 반환
        TokenResponse tokens = naverLoginService.login(request.code(), request.state(), ipAddress);
        AccessTokenResponse atk = new AccessTokenResponse(tokens.atk());

        // [3] rtk는 쿠키에 넣어준다.
        cookieHandler.putRtkInCookie(httpServletResponse, tokens.rtk());

        // [4] atk는 요청 본문으로 반환한다.
        return ResponseEntity.ok().body(atk);
    }
}
