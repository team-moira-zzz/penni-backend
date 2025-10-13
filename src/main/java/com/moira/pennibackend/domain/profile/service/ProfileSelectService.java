package com.moira.pennibackend.domain.profile.service;

import com.moira.pennibackend.domain.profile.dto.response.ProfileResponse;
import com.moira.pennibackend.domain.profile.mapper.ProfileMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileSelectService {
    private final ProfileMapper profileMapper;

    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        return profileMapper.selectMyProfile(userId);
    }
}
