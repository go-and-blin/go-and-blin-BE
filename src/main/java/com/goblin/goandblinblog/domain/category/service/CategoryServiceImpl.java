package com.goblin.goandblinblog.domain.category.service;

import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.global.exception.category.CategoryExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void create(CategoryCreateServiceRequest request) {
        if(categoryRepository.existsByCategoryTypeAndTitle(request.categoryType(), request.title())){
            throw new CategoryExistsException();
        }
        categoryRepository.save(request.toEntity());
    }
}
