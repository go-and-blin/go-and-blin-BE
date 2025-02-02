package com.goblin.goandblinblog.external.s3.service;

import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
public class S3StorageService implements StorageProvider {

    private final StorageProvider storageProvider;

    @Override
    public String uploadProfileImage(MultipartFile file) {
        return storageProvider.uploadProfileImage(file);
    }

}