package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.global.entity.AccountBookCategory;
import com.moira.pennibackend.global.entity.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.moira.pennibackend.global.constant.PenniConstant.DEFAULT_EXPENSE_CATEGORY_NAMES;
import static com.moira.pennibackend.global.constant.PenniConstant.DEFAULT_INCOME_CATEGORY_NAMES;

@RequiredArgsConstructor
@Service
public class DefaultCategoryAddService {
    private final CategoryMapper categoryMapper;

    @Transactional
    public void addDefaultCategories(String groupId) {
        // [1] 수입 카테고리 추가
        long displayOrder = 0;

        for (String name : DEFAULT_INCOME_CATEGORY_NAMES) {
            AccountBookCategory accountBookCategory = new AccountBookCategory(
                    groupId,
                    CategoryType.INCOME,
                    name,
                    ++displayOrder
            );

            categoryMapper.insertCategory(accountBookCategory);
        }

        // [2] 지출 카테고리 추가
        displayOrder = 0;

        for (String name : DEFAULT_EXPENSE_CATEGORY_NAMES) {
            AccountBookCategory accountBookCategory = new AccountBookCategory(
                    groupId,
                    CategoryType.EXPENSE,
                    name,
                    ++displayOrder
            );

            categoryMapper.insertCategory(accountBookCategory);
        }
    }
}
