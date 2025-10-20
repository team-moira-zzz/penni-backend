package com.moira.pennibackend.domain.entry.controller;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryUpdateRequest;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryResponse;
import com.moira.pennibackend.domain.entry.dto.response.DailyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.dto.response.MonthlyEntryTotalResponse;
import com.moira.pennibackend.domain.entry.service.EntryAddService;
import com.moira.pennibackend.domain.entry.service.EntryDeleteService;
import com.moira.pennibackend.domain.entry.service.EntrySelectService;
import com.moira.pennibackend.domain.entry.service.EntryUpdateService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EntryController {
    private final EntryAddService entryAddService;
    private final EntryDeleteService entryDeleteService;
    private final EntrySelectService entrySelectService;
    private final EntryUpdateService entryUpdateService;

    @PostMapping("/api/group/{groupId}/entries")
    ResponseEntity<Object> addEntry(
            @PathVariable String groupId,
            @RequestBody AccountBookEntryAddRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        entryAddService.addEntry(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/api/group/{groupId}/entries/{year}/{month}/{day}")
    ResponseEntity<List<DailyEntryResponse>> getDailyEntries(
            @PathVariable String groupId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day,
            @RequestParam String sort
    ) {
        List<DailyEntryResponse> result = entrySelectService.getDailyEntries(groupId, year, month, day, sort);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/group/{groupId}/entries/total/{year}/{month}/{day}")
    ResponseEntity<DailyEntryTotalResponse> getDailyEntriesTotal(
            @PathVariable String groupId,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day
    ) {
        DailyEntryTotalResponse result = entrySelectService.getDailyEntriesTotal(groupId, year, month, day);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/group/{groupId}/entries/total/{year}/{month}")
    ResponseEntity<MonthlyEntryTotalResponse> getMonthlyDailyEntriesTotal(
            @PathVariable String groupId,
            @PathVariable Integer year,
            @PathVariable Integer month
    ) {
        MonthlyEntryTotalResponse result = entrySelectService.getMonthlyEntriesTotal(groupId, year, month);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/group/{groupId}/entries/{entryId}")
    ResponseEntity<Object> updateEntry(
            @PathVariable String groupId,
            @PathVariable String entryId,
            @RequestBody AccountBookEntryUpdateRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        entryUpdateService.update(groupId, entryId, request, simpleUserAuth);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/group/{groupId}/entries/{entryId}")
    ResponseEntity<Object> deleteEntry(
            @PathVariable String groupId,
            @PathVariable String entryId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        entryDeleteService.delete(groupId, entryId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
