package com.moira.pennibackend.domain.entry.mapper;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryUpdateRequest;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.global.entity.AccountBookEntry;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EntryMapper {
    // 가계부 항목 검증 (그룹 포함 여부 확인)
    int checkGroupEntry(String groupId, String entryId);

    // 가계부 항목 추가
    void insertEntry(AccountBookEntry accountBookEntry);

    // 일별 가계부 항목 조회
    List<DailyEntryResponse> selectDailyEntryList(String groupId, LocalDate date);

    DailyEntryTotalResponse selectDailyEntryTotal(String groupId, LocalDate date);

    // 월별 가계부 항목 조회
    MonthlyEntryTotalResponse selectMonthlyEntryTotal(String groupId, String dateString);

    // 가계부 항목 수정
    void updateEntry(AccountBookEntryUpdateRequest request, String groupId, String entryId);

    // 가계부 항목 삭제
    void deleteEntry(String groupId, String entryId);
}
