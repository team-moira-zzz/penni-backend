package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.global.entity.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategorySelectService {
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getIncomeCategories(String groupId) {
        return categoryMapper.selectCategoryList(groupId, CategoryType.INCOME.name());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getExpenseCategories(String groupId) {
        return categoryMapper.selectCategoryList(groupId, CategoryType.EXPENSE.name());
    }
}
