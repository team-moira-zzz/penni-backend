package com.moira.pennibackend.domain.profile.service;

import com.moira.pennibackend.domain.profile.dto.response.NicknameUpdateRequest;
import com.moira.pennibackend.domain.profile.mapper.ProfileMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NicknameUpdateService {
    private final ProfileMapper profileMapper;

    private void validate(String userId, String newNickname) {
        int count = profileMapper.checkNickname(userId, newNickname);

        if (count > 0) {
            throw new PenniUserException(ErrorCode.ALREADY_USING_NICKNAME, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(NicknameUpdateRequest request, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();
        String newNickname = request.newNickname();

        // [1] 유효성 검사
        this.validate(userId, newNickname);

        // [2] 닉네임 변경
        profileMapper.updateNickname(userId, newNickname);
    }
}
