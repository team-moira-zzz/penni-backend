package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseAddRequest;
import com.moira.pennibackend.global.entity.enums.FixedExpenseCycle;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class AccountBookFixedExpense {
    private String id;
    private String groupId;
    private String userId;
    private String content;
    private String description;
    private FixedExpenseCycle cycle;
    private Integer monthlyDay;
    private Integer annualMonth;
    private Integer annualDay;
    private int price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public AccountBookFixedExpense(String groupId, String userId, FixedExpenseAddRequest request) {
        this.id = UUID.randomUUID().toString();
        this.groupId = groupId;
        this.userId = userId;
        this.content = request.content();
        this.description = request.description();
        this.cycle = FixedExpenseCycle.valueOf(request.cycle());
        this.monthlyDay = request.monthlyDay();
        this.annualMonth = request.annualMonth();
        this.annualDay = request.annualDay();
        this.price = request.price();
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
}
