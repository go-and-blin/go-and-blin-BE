package com.goblin.goandblinblog.domain.image.repository;

import com.goblin.goandblinblog.domain.image.entity.Image;
import com.goblin.goandblinblog.domain.image.service.port.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Image save(Image image) {
        return imageJpaRepository.save(image);
    }
}