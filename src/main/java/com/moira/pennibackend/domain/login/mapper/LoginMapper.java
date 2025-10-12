package com.moira.pennibackend.domain.login.mapper;

import com.moira.pennibackend.global.entity.User;
import com.moira.pennibackend.global.entity.UserLoginHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    // 회원가입
    void insertUser(User user);

    // 로그인
    User selectUserByEmail(String email);

    void updateUserWhenLogin(String id, String rtk);

    void insertUserLoginHistory(UserLoginHistory userLoginHistory);
}
