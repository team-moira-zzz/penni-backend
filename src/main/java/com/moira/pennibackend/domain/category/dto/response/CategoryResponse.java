package com.moira.pennibackend.domain.category.dto.response;

public record CategoryResponse(
        Long id,
        String type,
        String name,
        int displayOrder
) {
}
