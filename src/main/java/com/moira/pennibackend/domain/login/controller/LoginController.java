package com.moira.pennibackend.domain.login.controller;

import com.moira.pennibackend.domain.login.dto.AccessTokenResponse;
import com.moira.pennibackend.domain.login.dto.KakaoCodeRequest;
import com.moira.pennibackend.domain.login.dto.TokenResponse;
import com.moira.pennibackend.domain.login.service.KakaoLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.moira.pennibackend.global.constant.PenniConstant.KAKAO_AUTH_URL;
import static com.moira.pennibackend.global.constant.PenniConstant.RTK_COOKIE_NAME;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final KakaoLoginService kakaoLoginService;

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    private void putRtkInCookie(HttpServletResponse response, String rtk) {
        Cookie cookie = new Cookie(RTK_COOKIE_NAME, rtk);

        // cookie.setSecure(true);         // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.setHttpOnly(true);          // JavaScript로 접근 불가능
        cookie.setPath("/");               // 모든 경로에서 쿠키 사용 가능
        cookie.setMaxAge(60 * 60 * 24);    // 1일

        response.addCookie(cookie);
    }

    @GetMapping("/login/kakao/redirect")
    public String kakaoLogin() {
        // [1] 리다이렉트
        String redirectStringFormat = "%s?response_type=code&client_id=%s&redirect_uri=%s";
        String redirectString = redirectStringFormat.formatted(KAKAO_AUTH_URL, kakaoClientId, kakaoRedirectUri);

        return "redirect:" + redirectString;
    }

    @PostMapping("/login/kakao")
    ResponseEntity<AccessTokenResponse> kakaoLogin(
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
        this.putRtkInCookie(httpServletResponse, tokens.rtk());

        // [4] atk는 요청 본문으로 반환한다.
        return ResponseEntity.ok().body(atk);
    }
}
