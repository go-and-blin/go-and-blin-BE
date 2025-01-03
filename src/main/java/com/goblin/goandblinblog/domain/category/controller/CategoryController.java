package com.goblin.goandblinblog.domain.category.controller;

import com.goblin.goandblinblog.domain.category.controller.dto.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequest request) {
        categoryService.create(request.toServiceRequest());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
