package com.moira.pennibackend.domain.group.controller;

import com.moira.pennibackend.domain.group.dto.response.GroupIdResponse;
import com.moira.pennibackend.domain.group.service.GroupCheckService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class GroupController {
    private final GroupCheckService groupCheckService;

    @GetMapping("/group/check")
    ResponseEntity<GroupIdResponse> checkGroupUser(@UserPrincipal SimpleUserAuth simpleUserAuth) {
        String groupId = groupCheckService.getGroupId(simpleUserAuth);
        GroupIdResponse groupIdResponse = new GroupIdResponse(groupId);

        return ResponseEntity.ok().body(groupIdResponse);
    }

}
