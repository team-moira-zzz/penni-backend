package com.moira.pennibackend.domain.fixed.service;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseAddRequest;
import com.moira.pennibackend.domain.fixed.mapper.FixedExpenseMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookFixedExpense;
import com.moira.pennibackend.global.entity.enums.FixedExpenseCycle;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.utility.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FixedExpenseAddService {
    private final EnumValidator enumValidator;
    private final FixedExpenseMapper fixedExpenseMapper;

    private void validate(String cycle) {
        enumValidator.validateStringValue(FixedExpenseCycle.class, cycle, ErrorCode.INVALID_FIXED_EXPENSE_CYCLE);
    }

    @Transactional
    public void add(FixedExpenseAddRequest request, String groupId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유효성 검사
        this.validate(request.cycle());

        // [2] DTO -> 엔티티 변환
        AccountBookFixedExpense accountBookFixedExpense = new AccountBookFixedExpense(groupId, userId, request);

        // [3] 저장
        fixedExpenseMapper.insertAccountBookFixedExpense(accountBookFixedExpense);
    }
}
