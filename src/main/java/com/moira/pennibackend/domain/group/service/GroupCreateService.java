package com.moira.pennibackend.domain.group.service;

import com.moira.pennibackend.domain.category.service.DefaultCategoryAddService;
import com.moira.pennibackend.domain.group.component.GroupCodeGenerator;
import com.moira.pennibackend.domain.group.dto.request.GroupCreateRequest;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookGroup;
import com.moira.pennibackend.global.entity.AccountBookGroupUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupCreateService {
    private final DefaultCategoryAddService defaultCategoryAddService;
    private final GroupCodeGenerator groupCodeGenerator;
    private final GroupMapper groupMapper;

    @Transactional
    public void createGroup(GroupCreateRequest request, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();
        String code;

        // [1] 그룹 가입 코드 생성 (중복 체크 로직 추가)
        do {
            code = groupCodeGenerator.generateRandomCode();
        }
        while (groupMapper.checkCode(code) >= 1);

        // [2] DTO -> 엔티티 변환
        AccountBookGroup accountBookGroup = new AccountBookGroup(request, userId, code);
        AccountBookGroupUser accountBookGroupUser = new AccountBookGroupUser(accountBookGroup.getId(), userId);

        // [3] DB에 그룹 및 그룹 유저를 저장
        groupMapper.insertAccountBookGroup(accountBookGroup);
        groupMapper.insertAccountBookGroupUser(accountBookGroupUser);

        // [4] 기본 카테고리 추가
        defaultCategoryAddService.addDefaultCategories(accountBookGroup.getId());
    }
}
