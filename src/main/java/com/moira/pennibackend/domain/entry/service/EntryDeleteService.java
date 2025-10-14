package com.moira.pennibackend.domain.entry.service;

import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.aop.GroupEntryCheck;
import com.moira.pennibackend.global.aop.GroupUserCheck;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EntryDeleteService {
    private final EntryMapper entryMapper;

    @GroupUserCheck
    @GroupEntryCheck
    @Transactional
    public void delete(String groupId, String entryId, SimpleUserAuth simpleUserAuth) {
        entryMapper.deleteEntry(groupId, entryId);
    }
}
