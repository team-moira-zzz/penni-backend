package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.global.entity.enums.UserRole;
import com.moira.pennibackend.global.entity.enums.UserStatus;
import com.moira.pennibackend.global.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private String id;
    private UserRole role;
    private UserStatus status;
    private UserType type;
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private String rtk;
    private OffsetDateTime lastLoginAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public User(String email, String nickname, UserType type) {
        this.id = UUID.randomUUID().toString();
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.type = type;
        this.email = email;
        this.name = null;
        this.nickname = nickname;
        this.phone = null;
        this.rtk = null;
        this.lastLoginAt = null;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    public User(String email, String name, String nickname, String mobile, UserType type) {
        this.id = UUID.randomUUID().toString();
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.type = type;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phone = mobile;
        this.rtk = null;
        this.lastLoginAt = null;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
