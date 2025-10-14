package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryUpdateRequest;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.aop.GroupEntryCheck;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
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
public class EntryUpdateService {
    private final EntryMapper entryMapper;

    private void validate(AccountBookEntryUpdateRequest request) {
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
    @GroupEntryCheck
    @Transactional
    public void update(String groupId, String entryId, AccountBookEntryUpdateRequest request, SimpleUserAuth simpleUserAuth) {
        entryMapper.updateEntry(request, groupId, entryId);
    }
}
