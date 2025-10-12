package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryDeleteService {
    private final CategoryMapper categoryMapper;
    private final GroupMapper groupMapper;

    private void validate(String groupId, String userId) {
        int count = groupMapper.checkGroupUser(groupId, userId);

        if (count < 1) {
            throw new PenniGroupException(ErrorCode.GROUP_ID_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void deleteCategory(Long categoryId, String groupId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유효성 검사
        this.validate(groupId, userId);

        // [2] DB에서 카테고리 삭제
        categoryMapper.deleteCategory(groupId, categoryId);
    }
}
