package com.moira.pennibackend.domain.fixed.service;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseUpdateRequest;
import com.moira.pennibackend.domain.fixed.mapper.FixedExpenseMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.enums.FixedExpenseCycle;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.utility.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FixedExpenseUpdateService {
    private final EnumValidator enumValidator;
    private final FixedExpenseMapper fixedExpenseMapper;

    private void validate(String cycle) {
        enumValidator.validateStringValue(FixedExpenseCycle.class, cycle, ErrorCode.INVALID_FIXED_EXPENSE_CYCLE);
    }

    @Transactional
    @GroupUserCheck
    public void update(FixedExpenseUpdateRequest request, String groupId, String fixedId, SimpleUserAuth simpleUserAuth) {
        // [1] 유효성 검사
        this.validate(request.cycle());

        // [2] 수정 저장
        fixedExpenseMapper.updateAccountBookFixedExpense(groupId, fixedId, request);
    }
}
