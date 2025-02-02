package com.goblin.goandblinblog.external.s3.provider;

import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import org.springframework.web.multipart.MultipartFile;

public class S3StorageProviderStub implements StorageProvider {

    private final String URL;

    public S3StorageProviderStub(String URL) {
        this.URL = URL;
    }

    @Override
    public String uploadProfileImage(MultipartFile file) {
        return URL;
    }

}