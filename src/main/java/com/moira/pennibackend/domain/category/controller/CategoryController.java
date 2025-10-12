package com.moira.pennibackend.domain.category.controller;

import com.moira.pennibackend.domain.category.dto.request.CategoryAddRequest;
import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.domain.category.service.CategoryAddService;
import com.moira.pennibackend.domain.category.service.CategoryDeleteService;
import com.moira.pennibackend.domain.category.service.CategorySelectService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CategoryController {
    private final CategoryAddService categoryAddService;
    private final CategoryDeleteService categoryDeleteService;
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

    @PostMapping("/group/{groupId}/category")
    ResponseEntity<Object> addCategory(
            @RequestBody CategoryAddRequest request,
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        categoryAddService.addCategory(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/group/{groupId}/category/{categoryId}")
    ResponseEntity<Object> deleteCategory(
            @PathVariable String groupId,
            @PathVariable Long categoryId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        categoryDeleteService.deleteCategory(categoryId, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
