package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookEntry;
import com.moira.pennibackend.global.entity.enums.AccountBookEntryMethod;
import com.moira.pennibackend.global.entity.enums.AccountBookEntryType;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EntryAddService {
    private final EntryMapper entryMapper;

    private void validate(AccountBookEntryAddRequest request) {
        try {
            AccountBookEntryType.valueOf(request.type());
        } catch (IllegalArgumentException e) {
            throw new PenniGroupException(ErrorCode.INVALID_CATEGORY_TYPE, HttpStatus.BAD_REQUEST);
        }

        try {
            AccountBookEntryMethod.valueOf(request.method());
        } catch (IllegalArgumentException e) {
            throw new PenniGroupException(ErrorCode.INVALID_METHOD_TYPE, HttpStatus.BAD_REQUEST);
        }
    }

    @GroupUserCheck
    @Transactional
    public void addEntry(AccountBookEntryAddRequest request, String groupId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유효성 검사
        this.validate(request);

        // [2] 가계부 항목 저장
        AccountBookEntry accountBookEntry = new AccountBookEntry(userId, groupId, request);
        entryMapper.insertEntry(accountBookEntry);
    }
}
