package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.domain.category.dto.request.CategoryAddRequest;
import com.moira.pennibackend.global.entity.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountBookCategory {
    private Long id;
    private CategoryType type;
    private String groupId;
    private String name;
    private int displayOrder;
    private LocalDateTime createdAt;

    public AccountBookCategory(CategoryAddRequest request, String groupId, int displayOrder) {
        this.type = CategoryType.valueOf(request.type());
        this.groupId = groupId;
        this.name = request.name();
        this.displayOrder = displayOrder;
        this.createdAt = LocalDateTime.now();
    }
}