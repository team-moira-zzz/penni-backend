package com.moira.pennibackend.domain.group.mapper;

import com.moira.pennibackend.global.entity.AccountBookGroup;
import com.moira.pennibackend.global.entity.AccountBookGroupUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    // 그룹 가입 여부 조회
    List<String> selectGroupId(String userId);

    // 그룹 생성 및 가입
    void insertAccountBookGroup(AccountBookGroup accountBookGroup);

    void insertAccountBookGroupUser(AccountBookGroupUser accountBookGroupUser);
}
