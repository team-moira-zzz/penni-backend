package com.moira.pennibackend.domain.fixed.mapper;

import com.moira.pennibackend.global.entity.AccountBookFixedExpense;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FixedExpenseMapper {
    // 고정 지출 저장
    void insertAccountBookFixedExpense(AccountBookFixedExpense accountBookFixedExpense);
}
