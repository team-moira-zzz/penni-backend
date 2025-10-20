package com.moira.pennibackend.domain.entry.mapper;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryUpdateRequest;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.global.entity.AccountBookEntry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EntryMapper {
    // 그룹 내에 존재하는 가계부 항목인지 확인
    int checkGroupEntry(String groupId, String entryId);

    // 가계부 항목 추가
    void insertEntry(AccountBookEntry accountBookEntry);

    // 일별 가계부 항목 조회
    List<DailyEntryResponse> selectDailyEntryList(String groupId, String dateString, String sort);

    // 일별 수입/지출 총합 조회
    DailyEntryTotalResponse selectDailyEntryTotal(String groupId, String dateString);

    // 월별 수입/지출 총합 조회
    MonthlyEntryTotalResponse selectMonthlyEntryTotal(String groupId, String dateString);

    // 가계부 항목 수정
    void updateEntry(AccountBookEntryUpdateRequest request, String groupId, String entryId);

    // 가계부 항목 삭제
    void deleteEntry(String groupId, String entryId);
}
