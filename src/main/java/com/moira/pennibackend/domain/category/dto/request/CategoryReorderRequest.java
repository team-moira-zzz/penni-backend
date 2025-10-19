package com.moira.pennibackend.domain.category.dto.request;

import java.util.List;

public record CategoryReorderRequest(String type, List<Long> categoryIds) {
}
