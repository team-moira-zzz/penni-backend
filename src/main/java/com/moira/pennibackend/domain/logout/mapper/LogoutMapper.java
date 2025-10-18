package com.moira.pennibackend.domain.logout.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogoutMapper {
    // 로그아웃 시 RTK 삭제
    void updateUserWhenLogout(String userId);
}
