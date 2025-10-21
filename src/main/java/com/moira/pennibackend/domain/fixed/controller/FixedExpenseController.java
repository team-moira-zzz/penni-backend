package com.moira.pennibackend.domain.fixed.controller;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseAddRequest;
import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseUpdateRequest;
import com.moira.pennibackend.domain.fixed.dto.response.FixedExpenseResponse;
import com.moira.pennibackend.domain.fixed.service.FixedExpenseAddService;
import com.moira.pennibackend.domain.fixed.service.FixedExpenseDeleteService;
import com.moira.pennibackend.domain.fixed.service.FixedExpenseSelectService;
import com.moira.pennibackend.domain.fixed.service.FixedExpenseUpdateService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FixedExpenseController {
    private final FixedExpenseAddService fixedExpenseAddService;
    private final FixedExpenseDeleteService fixedExpenseDeleteService;
    private final FixedExpenseSelectService fixedExpenseSelectService;
    private final FixedExpenseUpdateService fixedExpenseUpdateService;

    @PostMapping("/api/group/{groupId}/fixed")
    ResponseEntity<Object> add(
            @RequestBody FixedExpenseAddRequest request,
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        fixedExpenseAddService.add(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/api/group/{groupId}/fixed")
    ResponseEntity<List<FixedExpenseResponse>> getAll(
            @PathVariable String groupId
    ) {
        List<FixedExpenseResponse> result = fixedExpenseSelectService.getAll(groupId);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/group/{groupId}/fixed/{fixedId}")
    ResponseEntity<Object> update(
            @RequestBody FixedExpenseUpdateRequest request,
            @PathVariable String groupId,
            @PathVariable String fixedId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        fixedExpenseUpdateService.update(request, groupId, fixedId, simpleUserAuth);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/group/{groupId}/fixed/{fixedId}")
    ResponseEntity<Object> delete(
            @PathVariable String groupId,
            @PathVariable String fixedId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        fixedExpenseDeleteService.delete(groupId, fixedId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
