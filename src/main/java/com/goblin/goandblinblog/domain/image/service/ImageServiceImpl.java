package com.goblin.goandblinblog.domain.image.service;

import com.goblin.goandblinblog.domain.image.controller.dto.response.ImageResponse;
import com.goblin.goandblinblog.domain.image.controller.port.ImageService;
import com.goblin.goandblinblog.domain.image.entity.Image;
import com.goblin.goandblinblog.domain.image.service.port.ImageRepository;
import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final StorageProvider storageProvider;

    @Override
    public ImageResponse upload(MultipartFile file, String postId) {
        String url = storageProvider.uploadPostImage(file);
        imageRepository.save(new Image(url, postId));
        return new ImageResponse(postId, url);
    }
}