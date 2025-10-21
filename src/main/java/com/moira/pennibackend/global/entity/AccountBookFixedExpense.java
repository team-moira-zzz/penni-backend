package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.global.entity.enums.FixedExpenseCycle;

import java.time.OffsetDateTime;

public class AccountBookFixedExpense {
    private String id;
    private String groupId;
    private String userId;
    private String content;
    private String description;
    private OffsetDateTime date;
    private FixedExpenseCycle cycle;
    private Integer monthlyDay;
    private Integer annualMonth;
    private Integer annualDay;
    private int price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
