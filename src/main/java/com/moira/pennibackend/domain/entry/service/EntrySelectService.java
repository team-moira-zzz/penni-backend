package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EntrySelectService {
    private final EntryMapper entryMapper;

    @Transactional(readOnly = true)
    public List<DailyEntryResponse> getDailyEntries(String groupId, LocalDate date) {
        return entryMapper.selectDailyEntryList(groupId, date);
    }

    @Transactional(readOnly = true)
    public DailyEntryTotalResponse getDailyEntriesTotal(String groupId, LocalDate date) {
        return entryMapper.selectDailyEntryTotal(groupId, date);
    }

    @Transactional(readOnly = true)
    public MonthlyEntryTotalResponse getMonthlyEntriesTotal(String groupId, String dateString) {
        return entryMapper.selectMonthlyEntryTotal(groupId, dateString);
    }
}
