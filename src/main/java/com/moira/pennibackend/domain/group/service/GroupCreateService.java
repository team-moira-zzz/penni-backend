package com.moira.pennibackend.domain.group.service;

import com.moira.pennibackend.domain.group.dto.request.GroupCreateRequest;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookGroup;
import com.moira.pennibackend.global.entity.AccountBookGroupUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

import static com.moira.pennibackend.global.constant.PenniConstant.CHARACTERS;
import static com.moira.pennibackend.global.constant.PenniConstant.CODE_LENGTH;

@RequiredArgsConstructor
@Service
public class GroupCreateService {
    private final GroupMapper groupMapper;
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    @Transactional
    public void createGroup(GroupCreateRequest request, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();
        String code = this.generateRandomCode();

        // [1] DTO -> 엔티티 변환
        AccountBookGroup accountBookGroup = new AccountBookGroup(request, userId, code);
        AccountBookGroupUser accountBookGroupUser = new AccountBookGroupUser(accountBookGroup.getId(), userId);

        // [2] DB에 그룹 및 그룹 유저를 저장
        groupMapper.insertAccountBookGroup(accountBookGroup);
        groupMapper.insertAccountBookGroupUser(accountBookGroupUser);
    }
}
