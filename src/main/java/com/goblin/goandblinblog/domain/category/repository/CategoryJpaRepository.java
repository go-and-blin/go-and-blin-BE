package com.goblin.goandblinblog.domain.category.repository;

import com.goblin.goandblinblog.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}