package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EntryDeleteService {
    private final EntryMapper entryMapper;
    private final GroupMapper groupMapper;

    private void validate(String groupId, String userId) {
        int count = groupMapper.checkGroupUser(groupId, userId);

        if (count < 1) {
            throw new PenniGroupException(ErrorCode.GROUP_ID_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void delete(String groupId, String entryId, SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 유효성 검사
        this.validate(groupId, userId);

        // [2] 삭제
        entryMapper.deleteEntry(groupId, entryId);
    }
}
