package com.goblin.goandblinblog.domain.image.repository;

import com.goblin.goandblinblog.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

}