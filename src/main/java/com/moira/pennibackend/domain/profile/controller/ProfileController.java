package com.moira.pennibackend.domain.profile.controller;

import com.moira.pennibackend.domain.profile.dto.response.ProfileResponse;
import com.moira.pennibackend.domain.profile.service.ProfileSelectService;
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
public class ProfileController {
    private final ProfileSelectService profileSelectService;

    @GetMapping("/me")
    ResponseEntity<ProfileResponse> getMyProfile(@UserPrincipal SimpleUserAuth simpleUserAuth) {
        ProfileResponse profileResponse = profileSelectService.getMyProfile(simpleUserAuth);

        return ResponseEntity.ok().body(profileResponse);
    }
}
