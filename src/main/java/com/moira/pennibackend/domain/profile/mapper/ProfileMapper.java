package com.moira.pennibackend.domain.profile.mapper;

import com.moira.pennibackend.domain.profile.dto.response.ProfileResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    // 내 프로필 조회
    ProfileResponse selectMyProfile(String userId);

    // 닉네임 변경
    int checkNickname(String userId, String newNickname);

    void updateNickname(String userId, String newNickname);
}
