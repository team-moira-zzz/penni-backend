package com.moira.pennibackend.domain.fixed.controller;

import com.moira.pennibackend.domain.fixed.dto.request.FixedExpenseAddRequest;
import com.moira.pennibackend.domain.fixed.service.FixedExpenseAddService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FixedExpenseController {
    private final FixedExpenseAddService fixedExpenseAddService;

    @PostMapping("/api/group/{groupId}/fixed")
    ResponseEntity<Object> add(
            @RequestBody FixedExpenseAddRequest request,
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        fixedExpenseAddService.add(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
