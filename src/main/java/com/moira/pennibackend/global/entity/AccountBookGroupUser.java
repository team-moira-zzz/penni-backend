package com.moira.pennibackend.global.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AccountBookGroupUser {
    private String groupId;
    private String userId;
    private OffsetDateTime joinedAt;
    private OffsetDateTime leftAt;

    public AccountBookGroupUser(String groupId, String userId) {
        this.groupId = groupId;
        this.userId = userId;
        this.joinedAt = OffsetDateTime.now();
        this.leftAt = null;
    }
}
