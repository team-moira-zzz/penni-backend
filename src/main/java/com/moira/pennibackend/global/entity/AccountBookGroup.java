package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.domain.group.dto.request.GroupCreateRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AccountBookGroup {
    private String id;
    private String userId;
    private String name;
    private String description;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public AccountBookGroup(GroupCreateRequest request, String userId, String code) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = request.name();
        this.description = request.description();
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.deletedAt = null;
    }
}