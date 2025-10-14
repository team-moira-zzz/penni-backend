package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryUpdateRequest;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
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
    private final GroupMapper groupMapper;

    private void validate(String groupId, String userId) {
        int count = groupMapper.checkGroupUser(groupId, userId);

        if (count < 1) {
            throw new PenniGroupException(ErrorCode.GROUP_ID_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }
    }

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

    // TODO: 추후 AOP로 어노테이션화 하기
    private void validate(String groupId, String entryId, Boolean todo) {
        int count = entryMapper.selectEntryChk(groupId, entryId);

        if (count < 1) {
            throw new PenniGroupException(ErrorCode.ENTRY_NOT_BELONGS_TO_GROUP, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void update(String groupId, String entryId, AccountBookEntryUpdateRequest request, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유효성 검사
        this.validate(groupId, userId);
        this.validate(groupId, entryId, true);

        // [2] 수정
        entryMapper.updateEntry(request, groupId, entryId);
    }
}
