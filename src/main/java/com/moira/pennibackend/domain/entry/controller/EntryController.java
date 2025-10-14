package com.moira.pennibackend.domain.entry.controller;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.service.DailyEntrySelectService;
import com.moira.pennibackend.domain.entry.service.EntryAddService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class EntryController {
    private final EntryAddService entryAddService;
    private final DailyEntrySelectService dailyEntrySelectService;

    @PostMapping("/group/{groupId}/entries")
    ResponseEntity<Object> addEntry(
            @PathVariable String groupId,
            @RequestBody AccountBookEntryAddRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        entryAddService.addEntry(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/group/{groupId}/entries/{year}/{month}/{day}")
    ResponseEntity<List<DailyEntryResponse>> getDailyEntries(
            @PathVariable String groupId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day
    ) {
        LocalDate date = LocalDate.of(year, month, day);
        List<DailyEntryResponse> list = dailyEntrySelectService.getDailyEntries(groupId, date);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/group/{groupId}/entries/total/{year}/{month}/{day}")
    ResponseEntity<DailyEntryTotalResponse> getDailyEntriesTotal(
            @PathVariable String groupId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day
    ) {
        LocalDate date = LocalDate.of(year, month, day);
        DailyEntryTotalResponse result = dailyEntrySelectService.getDailyEntriesTotal(groupId, date);

        return ResponseEntity.ok(result);
    }
}
