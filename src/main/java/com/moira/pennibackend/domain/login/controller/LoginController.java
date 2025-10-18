package com.moira.pennibackend.domain.login.controller;

import com.moira.pennibackend.domain.login.dto.request.KakaoCodeRequest;
import com.moira.pennibackend.domain.login.dto.request.NaverCodeRequest;
import com.moira.pennibackend.domain.login.dto.response.AccessTokenResponse;
import com.moira.pennibackend.domain.login.dto.response.TokenResponse;
import com.moira.pennibackend.domain.login.service.KakaoLoginService;
import com.moira.pennibackend.domain.login.service.NaverLoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.moira.pennibackend.global.constant.PenniConstant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final KakaoLoginService kakaoLoginService;
    private final NaverLoginService naverLoginService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${kakao.client_id}")
    private String kakaoClientId;

    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${naver.client_id}")
    private String naverClientId;

    @Value("${naver.redirect_uri}")
    private String naverRedirectUri;

    private void putRtkInCookie(HttpServletResponse response, String rtk) {
        Cookie cookie = new Cookie(RTK_COOKIE_NAME, rtk);

        // cookie.setSecure(true);         // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.setHttpOnly(true);          // JavaScript로 접근 불가능
        cookie.setPath("/");               // 모든 경로에서 쿠키 사용 가능
        cookie.setMaxAge(60 * 60 * 24);    // 1일

        response.addCookie(cookie);
    }

    private void putStateInRedis(String state) {
        redisTemplate.opsForValue().set("naver:state:" + state, true, 5, TimeUnit.MINUTES);
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

    @GetMapping("/login/naver/redirect")
    public String naverLogin() {
        // [1] state 생성 후 Redis에 대입
        String state = UUID.randomUUID().toString();
        this.putStateInRedis(state);

        // [2] 리다이렉트
        String redirectStringFormat = "%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s";
        String redirectString = redirectStringFormat.formatted(NAVER_AUTH_URL, naverClientId, naverRedirectUri, state);

        return "redirect:" + redirectString;
    }

    @PostMapping("/login/naver")
    ResponseEntity<AccessTokenResponse> naverLogin(
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
        this.putRtkInCookie(httpServletResponse, tokens.rtk());

        // [4] atk는 요청 본문으로 반환한다.
        return ResponseEntity.ok().body(atk);
    }
}
