package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.entity.enums.EntrySortCondition;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.utility.EnumValidator;
import com.moira.pennibackend.global.utility.StringDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EntrySelectService {
    private final EntryMapper entryMapper;
    private final EnumValidator enumValidator;
    private final StringDateFormatter stringDateFormatter;

    private void validate(String sort) {
        enumValidator.validateStringValue(EntrySortCondition.class, sort, ErrorCode.INVALID_SORT_CONDITION);
    }

    @Transactional(readOnly = true)
    public List<DailyEntryResponse> getDailyEntries(String groupId, int year, int month, int day, String sort) {
        // [1] 유효성 검사
        if (sort != null) {
            this.validate(sort);
        }

        // [2] 일별 가계부 항목 조회
        String dateString = stringDateFormatter.getYYYYMMDDStr(year, month, day);
        return entryMapper.selectDailyEntryList(
                groupId,
                dateString,
                sort != null ? sort : EntrySortCondition.OLDEST.name()
        );
    }

    @Transactional(readOnly = true)
    public DailyEntryTotalResponse getDailyEntriesTotal(String groupId, int year, int month, int day) {
        String dateString = stringDateFormatter.getYYYYMMDDStr(year, month, day);
        return entryMapper.selectDailyEntryTotal(groupId, dateString);
    }

    @Transactional(readOnly = true)
    public MonthlyEntryTotalResponse getMonthlyEntriesTotal(String groupId, int year, int month) {
        String dateString = stringDateFormatter.getYYYYMMStr(year, month);
        return entryMapper.selectMonthlyEntryTotal(groupId, dateString);
    }
}
