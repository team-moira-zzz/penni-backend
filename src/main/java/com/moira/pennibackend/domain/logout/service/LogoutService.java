package com.moira.pennibackend.domain.logout.service;

import com.moira.pennibackend.domain.logout.mapper.LogoutMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LogoutService {
    private final LogoutMapper logoutMapper;

    @Transactional
    public void logout(SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // rtk 삭제
        logoutMapper.updateUserWhenLogout(userId);
    }
}
