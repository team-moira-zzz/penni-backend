package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.global.entity.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserLoginHistory {
    private String id;
    private String userId;
    private String ipAddress;
    private UserType loginType;
    private OffsetDateTime loginAt;
    private OffsetDateTime logoutAt;

    public UserLoginHistory(User user, String ipAddress) {
        this.id = UUID.randomUUID().toString();
        this.userId = user.getId();
        this.ipAddress = ipAddress;
        this.loginType = user.getType();
        this.loginAt = OffsetDateTime.now();
        this.logoutAt = null;
    }
}