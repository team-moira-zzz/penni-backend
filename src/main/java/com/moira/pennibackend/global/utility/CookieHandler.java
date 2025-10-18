package com.moira.pennibackend.global.utility;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import static com.moira.pennibackend.global.constant.PenniConstant.RTK_COOKIE_NAME;

@Component
public class CookieHandler {
    public void putRtkInCookie(HttpServletResponse response, String rtk) {
        Cookie cookie = new Cookie(RTK_COOKIE_NAME, rtk);

        // cookie.setSecure(true);         // HTTPS 연결에서만 전송 (운영 환경에서는 주석 해제)
        cookie.setHttpOnly(true);          // JavaScript로 접근 불가능
        cookie.setPath("/");               // 모든 경로에서 쿠키 사용 가능
        cookie.setMaxAge(60 * 60 * 24);    // 1일

        response.addCookie(cookie);
    }
}
