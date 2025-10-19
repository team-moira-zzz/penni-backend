package com.moira.pennibackend.domain.category.controller;

import com.moira.pennibackend.domain.category.dto.request.CategoryAddRequest;
import com.moira.pennibackend.domain.category.dto.request.CategoryReorderRequest;
import com.moira.pennibackend.domain.category.dto.response.CategoryResponse;
import com.moira.pennibackend.domain.category.service.CategoryAddService;
import com.moira.pennibackend.domain.category.service.CategoryDeleteService;
import com.moira.pennibackend.domain.category.service.CategoryReorderService;
import com.moira.pennibackend.domain.category.service.CategorySelectService;
import com.moira.pennibackend.global.auth.SimpleUserAuth;
import com.moira.pennibackend.global.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryAddService categoryAddService;
    private final CategoryDeleteService categoryDeleteService;
    private final CategoryReorderService categoryReorderService;
    private final CategorySelectService categorySelectService;

    @GetMapping("/api/group/{groupId}/category/income")
    ResponseEntity<List<CategoryResponse>> getIncomeCategories(@PathVariable String groupId) {
        List<CategoryResponse> list = categorySelectService.getIncomeCategories(groupId);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/api/group/{groupId}/category/expense")
    ResponseEntity<List<CategoryResponse>> getExpenseCategories(@PathVariable String groupId) {
        List<CategoryResponse> list = categorySelectService.getExpenseCategories(groupId);

        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/api/group/{groupId}/category")
    ResponseEntity<Object> addCategory(
            @RequestBody CategoryAddRequest request,
            @PathVariable String groupId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        categoryAddService.add(request, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/api/group/{groupId}/category/order")
    ResponseEntity<Object> reorder(
            @PathVariable String groupId,
            @RequestBody CategoryReorderRequest request,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        categoryReorderService.reorder(groupId, request, simpleUserAuth);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/group/{groupId}/category/{categoryId}")
    ResponseEntity<Object> delete(
            @PathVariable String groupId,
            @PathVariable Long categoryId,
            @UserPrincipal SimpleUserAuth simpleUserAuth
    ) {
        categoryDeleteService.delete(categoryId, groupId, simpleUserAuth);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
