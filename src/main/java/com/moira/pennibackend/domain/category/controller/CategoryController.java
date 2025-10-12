package com.moira.pennibackend.domain.category.controller;

import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.domain.category.service.CategorySelectService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CategoryController {
    private final CategorySelectService categorySelectService;

    @GetMapping("/group/{groupId}/category/income")
    ResponseEntity<List<CategoryResponse>> getIncomeCategories(
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        List<CategoryResponse> list = categorySelectService.getIncomeCategories(groupId, simpleUserAuth);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/group/{groupId}/category/expense")
    ResponseEntity<List<CategoryResponse>> getExpenseCategories(
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        List<CategoryResponse> list = categorySelectService.getExpenseCategories(groupId, simpleUserAuth);

        return ResponseEntity.ok().body(list);
    }
}
