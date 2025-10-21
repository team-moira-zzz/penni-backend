package com.moira.pennibackend.global.entity;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.global.entity.enums.PaymentMethod;
import com.moira.pennibackend.global.entity.enums.AccountBookEntryType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class AccountBookEntry {
    private String id;
    private String userId;
    private String groupId;
    private Long categoryId;
    private String content;
    private String description;
    private Integer price;
    private LocalDate date;
    private AccountBookEntryType type;
    private PaymentMethod method;
    private OffsetDateTime createdAt;

    public AccountBookEntry(String userId, String groupId, AccountBookEntryAddRequest request) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.groupId = groupId;
        this.categoryId = request.categoryId();
        this.content = request.content();
        this.description = request.description();
        this.price = request.price();
        this.date = request.date();
        this.type = AccountBookEntryType.valueOf(request.type());
        this.method = PaymentMethod.valueOf(request.method());
        this.createdAt = OffsetDateTime.now();
    }

    public AccountBookEntry(String userId, String groupId, LocalDate localDate, AccountBookFixedExpense accountBookFixedExpense) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.groupId = groupId;
        this.categoryId = null;
        this.content = accountBookFixedExpense.getContent();
        this.description = accountBookFixedExpense.getDescription();
        this.date = localDate;
        this.type = AccountBookEntryType.EXPENSE;
        this.method = accountBookFixedExpense.getMethod();
        this.createdAt = OffsetDateTime.now();

    }
}