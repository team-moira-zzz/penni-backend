package com.moira.pennibackend.domain.category.service;

import com.moira.pennibackend.domain.category.dto.request.CategoryReorderRequest;
import com.moira.pennibackend.domain.category.mapper.CategoryMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CategoryReorderService {
    private final CategoryMapper categoryMapper;

    @Transactional
    public void reorder(String groupId, CategoryReorderRequest request, SimpleUserAuth simpleUserAuth) {
        long newDisplayOrder = 0L;
        Map<String, Object> sendMap = new HashMap<>();

        for (Long categoryId : request.categoryIds()) {
            sendMap.put("groupId", groupId);
            sendMap.put("type", request.type());
            sendMap.put("categoryId", categoryId);
            sendMap.put("displayOrder", ++newDisplayOrder);

            categoryMapper.updateCategoryOrder(sendMap);
        }
    }
}
