package com.moira.pennibackend.domain.login.component;

import com.moira.pennibackend.domain.login.dto.kakao.KakaoTokenResponse;
import com.moira.pennibackend.domain.login.dto.kakao.KakaoUserInfoResponse;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static com.moira.pennibackend.global.constant.PenniConstant.KAKAO_TOKEN_URL;
import static com.moira.pennibackend.global.constant.PenniConstant.KAKAO_USER_INFO_URL;

@Component
@RequiredArgsConstructor
@Slf4j
public class OauthRequestSender {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.client_id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.redirect_uri}")
    private String KAKAO_REDIRECT_URI;

    private HttpEntity<MultiValueMap<String, String>> getTokenHttpEntity(String code) {
        // [1] Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // [2] Http Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", KAKAO_REDIRECT_URI);
        body.add("client_id", KAKAO_CLIENT_ID);

        // [3] Http Header + Http Body
        return new HttpEntity<>(body, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> getUserInfoHttpEntity(String accessToken) {
        // [1] Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // [2] Http Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // [3] Http Header + Http Body
        return new HttpEntity<>(body, headers);
    }

    public String getToken(String code) {
        // [1] Http 구조 생성
        HttpEntity<MultiValueMap<String, String>> request = getTokenHttpEntity(code);

        try {
            // [2] 카카오 API로 POST 요청 전송
            ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                    KAKAO_TOKEN_URL,
                    HttpMethod.POST,
                    request,
                    KakaoTokenResponse.class
            );

            // [3] 응답에서 AccessToken 추출
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().accessToken();
            } else {
                log.error(
                        "[카카오 토큰 발급 오류] {}. HTTP Status: {}, Response Body: {}",
                        ErrorCode.KAKAO_LOGIN_TOKEN_ERROR.getMessage(),
                        response.getStatusCode(),
                        response.getBody()
                );
                throw new PenniUserException(ErrorCode.KAKAO_LOGIN_TOKEN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("[카카오 로그인 오류] {}", ErrorCode.KAKAO_LOGIN_ERROR.getMessage(), e);

            throw new PenniUserException(ErrorCode.KAKAO_LOGIN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        // [1] Http 구조 생성
        HttpEntity<MultiValueMap<String, String>> request = getUserInfoHttpEntity(accessToken);

        try {
            // [2] 카카오 API로 POST 요청 전송
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.POST,
                    request,
                    KakaoUserInfoResponse.class
            );

            // [3] 응답에서 유저 정보 추출
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error(
                        "[카카오 유저 정보 조회 오류] {}. HTTP Status: {}, Response Body: {}",
                        ErrorCode.KAKAO_LOGIN_USER_INFO_ERROR.getMessage(),
                        response.getStatusCode(),
                        response.getBody()
                );
                throw new PenniUserException(ErrorCode.KAKAO_LOGIN_USER_INFO_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("[카카오 로그인 오류] {}", ErrorCode.KAKAO_LOGIN_ERROR.getMessage(), e);

            throw new PenniUserException(ErrorCode.KAKAO_LOGIN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
