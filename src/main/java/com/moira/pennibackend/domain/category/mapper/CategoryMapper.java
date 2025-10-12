package com.moira.pennibackend.domain.category.mapper;

import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<CategoryResponse> selectCategoryList(String groupId, String type);
}
