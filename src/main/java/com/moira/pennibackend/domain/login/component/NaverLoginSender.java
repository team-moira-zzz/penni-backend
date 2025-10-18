package com.moira.pennibackend.domain.login.component;

import com.moira.pennibackend.domain.login.dto.response.naver.NaverTokenResponse;
import com.moira.pennibackend.domain.login.dto.response.naver.NaverUserInfoResponse;
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

import static com.moira.pennibackend.global.constant.PenniConstant.NAVER_TOKEN_URL;
import static com.moira.pennibackend.global.constant.PenniConstant.NAVER_USER_INFO_URL;

@Component
@RequiredArgsConstructor
@Slf4j
public class NaverLoginSender {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${naver.client_id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client_secret}")
    private String NAVER_CLIENT_SECRET;

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

    private HttpEntity<MultiValueMap<String, String>> getTokenHttpEntity(String code, String state) {
        // [1] Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // [2] Http Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", NAVER_CLIENT_ID);
        body.add("client_secret", NAVER_CLIENT_SECRET);
        body.add("code", code);
        body.add("state", state);

        // [3] Http Header + Http Body
        return new HttpEntity<>(body, headers);
    }

    public String getToken(String code, String state) {
        // [1] Http 구조 생성
        HttpEntity<MultiValueMap<String, String>> request = getTokenHttpEntity(code, state);

        try {
            // [2] 네이버 API로 POST 요청 전송
            ResponseEntity<NaverTokenResponse> response = restTemplate.exchange(
                    NAVER_TOKEN_URL,
                    HttpMethod.POST,
                    request,
                    NaverTokenResponse.class
            );

            // [3] 응답에서 AccessToken 추출
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().accessToken();
            } else {
                log.error(
                        "[네이버 토큰 발급 오류] {}. HTTP Status: {}, Response Body: {}",
                        ErrorCode.NAVER_LOGIN_TOKEN_ERROR.getMessage(),
                        response.getStatusCode(),
                        response.getBody()
                );
                throw new PenniUserException(ErrorCode.NAVER_LOGIN_TOKEN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("[네이버 로그인 오류] {}", ErrorCode.NAVER_LOGIN_ERROR.getMessage(), e);

            throw new PenniUserException(ErrorCode.NAVER_LOGIN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public NaverUserInfoResponse getUserInfo(String accessToken) {
        // [1] Http 구조 생성
        HttpEntity<MultiValueMap<String, String>> request = getUserInfoHttpEntity(accessToken);

        try {
            // [2] 네이버 API로 POST 요청 전송
            ResponseEntity<NaverUserInfoResponse> response = restTemplate.exchange(
                    NAVER_USER_INFO_URL,
                    HttpMethod.POST,
                    request,
                    NaverUserInfoResponse.class
            );

            // [3] 응답에서 유저 정보 추출
            if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error(
                        "[네이버 유저 정보 조회 오류] {}. HTTP Status: {}, Response Body: {}",
                        ErrorCode.NAVER_LOGIN_USER_INFO_ERROR.getMessage(),
                        response.getStatusCode(),
                        response.getBody()
                );
                throw new PenniUserException(ErrorCode.NAVER_LOGIN_USER_INFO_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("[네이버 로그인 오류] {}", ErrorCode.NAVER_LOGIN_ERROR.getMessage(), e);

            throw new PenniUserException(ErrorCode.NAVER_LOGIN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
