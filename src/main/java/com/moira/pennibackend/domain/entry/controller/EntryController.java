package com.moira.pennibackend.domain.entry.controller;

import com.moira.pennibackend.domain.entry.dto.request.AccountBookEntryAddRequest;
import com.moira.pennibackend.domain.entry.service.EntryAddService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class EntryController {
    private final EntryAddService entryAddService;

    @PostMapping("/group/{groupId}/entries")
    ResponseEntity<Object> addEntry(
            @PathVariable String groupId,
            @RequestBody AccountBookEntryAddRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        entryAddService.addEntry(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
