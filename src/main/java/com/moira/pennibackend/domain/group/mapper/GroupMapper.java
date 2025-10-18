package com.moira.pennibackend.domain.group.mapper;

import com.moira.pennibackend.domain.group.dto.response.GroupResponse;
import com.moira.pennibackend.domain.group.dto.response.InviteCodeResponse;
import com.moira.pennibackend.global.entity.AccountBookGroup;
import com.moira.pennibackend.global.entity.AccountBookGroupUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupMapper {
    // 가입된 그룹의 ID 조회
    List<String> selectGroupId(String userId);

    // 코드 중복 여부 체크
    int checkCode(String code);

    int checkGroupUser(String groupId, String userId);

    // 그룹 생성
    void insertAccountBookGroup(AccountBookGroup accountBookGroup);

    // 그룹 가입
    void insertAccountBookGroupUser(AccountBookGroupUser accountBookGroupUser);

    // 초대 코드 조회
    InviteCodeResponse selectCode(String groupId);

    // 그룹 정보 조회
    GroupResponse selectGroupByCode(String code);
}
