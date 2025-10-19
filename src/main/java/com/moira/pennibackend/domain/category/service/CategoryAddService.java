package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.dto.request.CategoryAddRequest;
import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookCategory;
import com.moira.pennibackend.global.entity.enums.CategoryType;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.utility.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryAddService {
    private final CategoryMapper categoryMapper;
    private final EnumValidator enumValidator;

    private void validate(CategoryAddRequest request) {
        enumValidator.validateStringValue(CategoryType.class, request.type(), ErrorCode.INVALID_CATEGORY_TYPE);
    }

    @Transactional
    @GroupUserCheck
    public void addCategory(CategoryAddRequest request, String groupId, SimpleUserAuth simpleUserAuth) {
        // [1] 유효성 검사
        this.validate(request);

        // [2] 새로운 displayOrder 생성
        Long lastDisplayOrder = categoryMapper.selectLastDisplayOrder(groupId);
        long nextDisplayOrder = lastDisplayOrder == null ? 1 : lastDisplayOrder + 1;

        // [3] AccountBookCategory 객체 생성
        AccountBookCategory accountBookCategory = new AccountBookCategory(request, groupId, nextDisplayOrder);

        // [4] DB에 카테고리 저장
        categoryMapper.insertCategory(accountBookCategory);
    }
}
