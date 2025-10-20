package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.utility.StringDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EntrySelectService {
    private final EntryMapper entryMapper;
    private final StringDateFormatter stringDateFormatter;

    @Transactional(readOnly = true)
    public List<DailyEntryResponse> getDailyEntries(String groupId, int year, int month, int day) {
        String dateString = stringDateFormatter.getYYYYMMDDStr(year, month, day);
        return entryMapper.selectDailyEntryList(groupId, dateString);
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
