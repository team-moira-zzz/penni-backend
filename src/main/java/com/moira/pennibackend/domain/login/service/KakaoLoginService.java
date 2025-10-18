package com.moira.pennibackend.domain.login.service;

import com.moira.pennibackend.domain.login.component.KakaoLoginSender;
import com.moira.pennibackend.domain.login.dto.response.TokenResponse;
import com.moira.pennibackend.domain.login.dto.response.kakao.KakaoUserInfoResponse;
import com.moira.pennibackend.domain.login.mapper.LoginMapper;
import com.moira.pennibackend.global.auth.JwtProvider;
import com.moira.pennibackend.global.entity.User;
import com.moira.pennibackend.global.entity.UserLoginHistory;
import com.moira.pennibackend.global.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {
    private final JwtProvider jwtProvider;
    private final KakaoLoginSender kakaoLoginSender;
    private final LoginMapper loginMapper;

    private TokenResponse createTokens(User user) {
        String atk = jwtProvider.createAtk(user);
        String rtk = jwtProvider.createRtk(user);

        return new TokenResponse(atk, rtk);
    }

    @Transactional
    public TokenResponse login(String code, String ipAddress) {
        // [1] 유저 정보 조회 (카카오 API 통신)
        String kakaoAtk = kakaoLoginSender.getToken(code);
        KakaoUserInfoResponse userInfo = kakaoLoginSender.getUserInfo(kakaoAtk);

        String email = userInfo.account().email();
        String nickname = userInfo.account().profile().nickname();
        User user = loginMapper.selectUserByEmail(email);

        // [2-1] 신규회원일 경우 회원가입 선행
        if (user == null) {
            User newUser = new User(email, nickname, UserType.KAKAO);
            loginMapper.insertUser(newUser);

            user = newUser;
        }

        // [2-2] JWT 토큰 생성
        TokenResponse tokens = createTokens(user);

        // [3] 로그인 기록 저장
        UserLoginHistory userLoginHistory = new UserLoginHistory(user, ipAddress);
        loginMapper.updateUserWhenLogin(user.getId(), tokens.rtk());
        loginMapper.insertUserLoginHistory(userLoginHistory);

        return tokens;
    }
}
