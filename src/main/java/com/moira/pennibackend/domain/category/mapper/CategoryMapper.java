package com.moira.pennibackend.domain.category.mapper;

import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.global.entity.AccountBookCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    // 카테고리 목록 조회
    List<CategoryResponse> selectCategoryList(String groupId, String type);

    // 카테고리 추가
    Integer selectLastDisplayOrder(String groupId);

    void insertCategory(AccountBookCategory accountBookCategory);
}
