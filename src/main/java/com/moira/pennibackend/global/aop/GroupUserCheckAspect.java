package com.moira.pennibackend.global.aop;

import com.moira.pennibackend.domain.group.mapper.GroupMapper;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
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
public class GroupUserCheckAspect {
    private final GroupMapper groupMapper;

    @Before("@annotation(com.moira.pennibackend.global.aop.GroupUserCheck)")
    public void validate(JoinPoint joinPoint) {
        // [1] 변수 세팅
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        String groupId = null;
        String userId = null;
        SimpleUserAuth simpleUserAuth = null;

        // [2] 메서드 파라미터 추출
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("groupId") && args[i] instanceof String) {
                groupId = (String) args[i];
            }
            // [2-2] SimpleUserAuth
            else if (args[i] instanceof SimpleUserAuth) {
                simpleUserAuth = (SimpleUserAuth) args[i];
                userId = simpleUserAuth.userId();
            }
        }

        // [3-1] 유효성 검사 : groupId나 simpleUserAuth가 null값인 경우
        if (groupId == null || simpleUserAuth == null) {
            log.error("필수 파라미터 누락. groupId({}) 혹은 simpleUserAuth({})", groupId, simpleUserAuth);
            throw new PenniGroupException(ErrorCode.INTERNAL_SYSTEM_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // [3-2] 유효성 검사 : 그룹에 포함된 유저가 아닌 경우
        if (groupMapper.checkGroupUser(groupId, userId) < 1) {
            throw new PenniGroupException(ErrorCode.GROUP_ID_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }
    }
}
