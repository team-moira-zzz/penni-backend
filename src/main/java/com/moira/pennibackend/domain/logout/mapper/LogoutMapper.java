package com.moira.pennibackend.domain.logout.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogoutMapper {
    void updateUserWhenLogout(String userId);
}
