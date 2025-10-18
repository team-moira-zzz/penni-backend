package com.moira.pennibackend.domain.logout.controller;

import com.moira.pennibackend.domain.logout.service.LogoutService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import com.moira.pennibackend.global.utility.CookieHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LogoutController {
    private final CookieHandler cookieHandler;
    private final LogoutService logoutService;

    @PostMapping("/api/logout")
    ResponseEntity<Object> logout(
            @UserPrincipal SimpleUserAuth simpleUserAuth,
            HttpServletResponse httpServletResponse
    ) {
        // [1] DB에서 RTK를 삭제
        logoutService.logout(simpleUserAuth);

        // [2] 쿠키에 담긴 rtk를 초기화
        cookieHandler.removeRtkFromCookie(httpServletResponse);

        return ResponseEntity.ok().body(null);
    }
}
