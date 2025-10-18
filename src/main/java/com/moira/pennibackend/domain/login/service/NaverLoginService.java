package com.moira.pennibackend.domain.login.service;

import com.moira.pennibackend.domain.login.component.NaverLoginSender;
import com.moira.pennibackend.domain.login.dto.response.TokenResponse;
import com.moira.pennibackend.domain.login.dto.response.naver.NaverUserInfoResponse;
import com.moira.pennibackend.domain.login.mapper.LoginMapper;
import com.moira.pennibackend.global.auth.JwtProvider;
import com.moira.pennibackend.global.entity.User;
import com.moira.pennibackend.global.entity.UserLoginHistory;
import com.moira.pennibackend.global.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NaverLoginService {
    private final JwtProvider jwtProvider;
    private final LoginMapper loginMapper;
    private final NaverLoginSender naverLoginSender;
    private final NaverLoginStateService naverLoginStateService;

    public TokenResponse login(String code, String state, String ipAddress) {
        // [1] state 검증
        naverLoginStateService.check(state);

        // [2] 유저 정보 조회 (네이버 API 통신)
        String naverAtk = naverLoginSender.getToken(code, state);
        NaverUserInfoResponse userInfo = naverLoginSender.getUserInfo(naverAtk);

        String email = userInfo.response().email();
        String name = userInfo.response().name();
        String nickname = userInfo.response().nickname();
        String mobile = userInfo.response().mobile();

        User user = loginMapper.selectUserByEmail(email);

        // [3-1] 신규회원일 경우 회원가입 선행
        if (user == null) {
            User newUser = new User(email, name, nickname, mobile, UserType.NAVER);
            loginMapper.insertUser(newUser);

            user = newUser;
        }

        // [3-2] JWT 토큰 생성
        TokenResponse tokens = jwtProvider.createTokens(user);

        // [4] 로그인 기록 저장
        UserLoginHistory userLoginHistory = new UserLoginHistory(user, ipAddress);
        loginMapper.updateUserWhenLogin(user.getId(), tokens.rtk());
        loginMapper.insertUserLoginHistory(userLoginHistory);

        return tokens;
    }
}
