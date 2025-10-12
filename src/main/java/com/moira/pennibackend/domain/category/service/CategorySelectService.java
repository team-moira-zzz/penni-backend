package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.enums.CategoryType;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategorySelectService {
    private final CategoryMapper categoryMapper;
    private final GroupMapper groupMapper;

    private void validate(String groupId, String userId) {
        int count = groupMapper.checkGroupUser(groupId, userId);

        if (count < 1) {
            throw new PenniGroupException(ErrorCode.GROUP_ID_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getIncomeCategories(String groupId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유저가 해당 그룹의 멤버인지 확인
        this.validate(groupId, userId);

        // [2] 수입 카테고리 목록 조회
        return categoryMapper.selectCategoryList(groupId, CategoryType.INCOME.name());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getExpenseCategories(String groupId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유저가 해당 그룹의 멤버인지 확인
        this.validate(groupId, userId);

        // [2] 지출 카테고리 목록 조회
        return categoryMapper.selectCategoryList(groupId, CategoryType.EXPENSE.name());
    }
}
