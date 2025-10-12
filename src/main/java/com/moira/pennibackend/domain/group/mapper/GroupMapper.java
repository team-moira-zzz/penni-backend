package com.moira.pennibackend.domain.group.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    // 그룹 가입 여부 조회
    List<String> selectGroupId(String userId);
}
