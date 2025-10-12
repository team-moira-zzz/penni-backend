package com.moira.pennibackend.domain.group.service;

import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class GroupCheckService {
    private final GroupMapper groupMapper;

    @Transactional(readOnly = true)
    public String getGroupId(SimpleUserAuth simpleUserAuth) {
        String userId = simpleUserAuth.userId();

        // [1] 가입된 그룹의 ID를 조회한다.
        List<String> groupIdList = groupMapper.selectGroupId(userId);

        // [2] 2개 이상의 그룹에 가입된 경우 에러를 발생한다.
        if (groupIdList.size() > 1) {
            log.error("[오류] 사용자(userId: {})가 2개 이상의 가계부 그룹에 가입되어 있습니다. Group IDs: {}", userId, groupIdList);

            throw new PenniGroupException(ErrorCode.JOINED_TWO_OR_MORE_GROUP, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // [3] 가입된 그룹 ID를 리턴하되, 가입된 그룹이 없는 경우에는 null을 리턴한다.
        return !groupIdList.isEmpty() ? groupIdList.getFirst() : null;
    }
}
