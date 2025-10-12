package com.moira.pennibackend.domain.group.controller;

import com.moira.pennibackend.domain.group.dto.request.GroupCreateRequest;
import com.moira.pennibackend.domain.group.dto.response.GroupIdResponse;
import com.moira.pennibackend.domain.group.service.GroupCheckService;
import com.moira.pennibackend.domain.group.service.GroupCreateService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GroupController {
    private final GroupCheckService groupCheckService;
    private final GroupCreateService groupCreateService;

    @GetMapping("/group/check")
    ResponseEntity<GroupIdResponse> checkGroupUser(@UserPrincipal SimpleUserAuth simpleUserAuth) {
        String groupId = groupCheckService.getGroupId(simpleUserAuth);
        GroupIdResponse groupIdResponse = new GroupIdResponse(groupId);

        return ResponseEntity.ok().body(groupIdResponse);
    }

    @PostMapping("/group")
    ResponseEntity<Object> createGroup(
            @RequestBody GroupCreateRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        groupCreateService.createGroup(request, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }

}
