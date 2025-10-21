package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.entity.AccountBookEntry;
import com.moira.pennibackend.global.entity.enums.PaymentMethod;
import com.moira.pennibackend.global.entity.enums.AccountBookEntryType;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.utility.EnumValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EntryAddService {
    private final EnumValidator enumValidator;
    private final EntryMapper entryMapper;

    private void validate(AccountBookEntryAddRequest request) {
        enumValidator.validateStringValue(AccountBookEntryType.class, request.type(), ErrorCode.INVALID_CATEGORY_TYPE);
        enumValidator.validateStringValue(PaymentMethod.class, request.method(), ErrorCode.INVALID_METHOD_TYPE);
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
