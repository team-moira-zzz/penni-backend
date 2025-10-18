package com.moira.pennibackend.domain.login.service;

import com.moira.pennibackend.global.exception.ErrorCode;
import com.moira.pennibackend.global.exception.custom.PenniUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.moira.pennibackend.global.constant.PenniConstant.NAVER_STATE_REDIS_PREFIX;

@RequiredArgsConstructor
@Service
@Slf4j
public class NaverLoginStateService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void put(String state) {
        redisTemplate.opsForValue().set(NAVER_STATE_REDIS_PREFIX + state, true, 5, TimeUnit.MINUTES);
    }

    public void check(String state) {
        if (!redisTemplate.hasKey(NAVER_STATE_REDIS_PREFIX + state)) {
            log.error(
                    "[네이버 로그인 오류] {}. state: {}",
                    ErrorCode.NAVER_LOGIN_INVALID_STATE.getMessage(), state
            );
            throw new PenniUserException(ErrorCode.NAVER_LOGIN_INVALID_STATE, HttpStatus.BAD_REQUEST);
        }
    }
}
