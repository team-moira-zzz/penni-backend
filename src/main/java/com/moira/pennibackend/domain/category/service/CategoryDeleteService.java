package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryDeleteService {
    private final CategoryMapper categoryMapper;

    @GroupUserCheck
    @Transactional
    public void delete(Long categoryId, String groupId, SimpleUserAuth simpleUserAuth) {
        categoryMapper.deleteCategory(groupId, categoryId);
    }
}
