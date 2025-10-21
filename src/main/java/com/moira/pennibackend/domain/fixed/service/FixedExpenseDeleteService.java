package com.moira.pennibackend.domain.fixed.service;

import com.moira.pennibackend.domain.fixed.mapper.FixedExpenseMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FixedExpenseDeleteService {
    private final FixedExpenseMapper fixedExpenseMapper;

    @Transactional
    @GroupUserCheck
    public void delete(String groupId, String fixedId, SimpleUserAuth simpleUserAuth) {
        fixedExpenseMapper.deleteAccountBookFixedExpense(groupId, fixedId);
    }
}
