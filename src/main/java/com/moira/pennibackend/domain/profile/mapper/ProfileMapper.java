package com.moira.pennibackend.domain.profile.mapper;

import com.moira.pennibackend.domain.profile.dto.response.ProfileResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {
    ProfileResponse selectMyProfile(String userId);
}
