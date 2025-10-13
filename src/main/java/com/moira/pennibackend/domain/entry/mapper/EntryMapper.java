package com.moira.pennibackend.domain.entry.mapper;

import com.moira.pennibackend.global.entity.AccountBookEntry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntryMapper {
    void insertEntry(AccountBookEntry accountBookEntry);
}
