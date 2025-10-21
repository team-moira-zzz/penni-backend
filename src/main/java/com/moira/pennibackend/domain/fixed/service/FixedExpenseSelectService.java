package com.moira.pennibackend.domain.fixed.service;

import com.moira.pennibackend.domain.fixed.dto.response.FixedExpenseResponse;
import com.moira.pennibackend.domain.fixed.mapper.FixedExpenseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FixedExpenseSelectService {
    private final FixedExpenseMapper fixedExpenseMapper;

    @Transactional(readOnly = true)
    public List<FixedExpenseResponse> getAll(String groupId) {
        return fixedExpenseMapper.selectAccountBookFixedExpenseList(groupId);
    }
}
