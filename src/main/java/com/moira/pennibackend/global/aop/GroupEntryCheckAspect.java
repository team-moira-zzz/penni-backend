package com.moira.pennibackend.global.aop;

import com.moira.pennibackend.domain.entry.mapper.EntryMapper;
import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniGroupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class GroupEntryCheckAspect {
    private final EntryMapper entryMapper;

    @Before("@annotation(com.moira.pennibackend.global.aop.GroupEntryCheck)")
    public void validate(JoinPoint joinPoint) {
        // [1] 변수 세팅
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        String groupId = null;
        String entryId = null;

        // [2] 메서드 파라미터 추출
        for (int i = 0; i < parameterNames.length; i++) {
            // [2-1] groupId
            if (parameterNames[i].equals("groupId") && args[i] instanceof String) {
                groupId = (String) args[i];
            }
            // [2-2] entryId
            else if (parameterNames[i].equals("entryId") && args[i] instanceof String) {
                entryId = (String) args[i];
            }
        }

        // [3-1] 유효성 검사 : groupId나 entryId null값인 경우
        if (groupId == null || entryId == null) {
            log.error("필수 파라미터 누락. groupId({}) 혹은 entryId({})", groupId, entryId);
            throw new PenniGroupException(ErrorCode.INTERNAL_SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // [3-2] 유효성 검사 : 그룹에 포함된 유저가 아닌 경우
        if (entryMapper.checkGroupEntry(groupId, entryId) < 1) {
            throw new PenniGroupException(ErrorCode.ENTRY_NOT_BELONGS_TO_GROUP, HttpStatus.UNAUTHORIZED);
        }
    }
}
