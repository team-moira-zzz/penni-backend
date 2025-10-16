package com.moira.pennibackend.domain.group.service;

import com.moira.pennibackend.domain.group.dto.response.InviteCodeResponse;
import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GroupSelectService {
    private final GroupMapper groupMapper;

    @Transactional(readOnly = true)
    public InviteCodeResponse getInviteCode(String groupId) {
        return groupMapper.selectCode(groupId);
    }
}
