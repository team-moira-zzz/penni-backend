package com.moira.pennibackend.domain.fixed.mapper;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseUpdateRequest;
import com.moira.pennibackend.domain.fixed.dto.response.FixedExpenseResponse;
import com.moira.pennibackend.global.entity.AccountBookFixedExpense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FixedExpenseMapper {
    // 고정 지출 저장
    void insertAccountBookFixedExpense(AccountBookFixedExpense accountBookFixedExpense);

    // 고정 지출 목록 조회
    List<FixedExpenseResponse> selectAccountBookFixedExpenseList(String groupId);

    // 고정 지출 수정
    void updateAccountBookFixedExpense(String groupId, String fixedId, @Param("request") FixedExpenseUpdateRequest fixedExpenseUpdateRequest);
}
