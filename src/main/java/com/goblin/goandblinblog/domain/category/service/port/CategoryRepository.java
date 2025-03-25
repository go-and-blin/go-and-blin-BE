package com.goblin.goandblinblog.domain.category.service.port;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import java.util.Collection;
import java.util.List;

public interface CategoryRepository {

    Category save(Category entity);

    boolean existsByCategoryTypeAndTitle(CategoryType categoryType, String title);

    Category findById(Long categoryId);

    void deleteAll();

    List<Category> findAll();
}