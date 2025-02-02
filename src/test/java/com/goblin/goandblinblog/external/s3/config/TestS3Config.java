package com.goblin.goandblinblog.external.s3.config;

import com.goblin.goandblinblog.external.s3.provider.S3StorageProviderStub;
import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestS3Config {

    public static final String TEST_URL_JPG = "test-url.jpg";

    @Bean
    public StorageProvider storageProvider() {
        return new S3StorageProviderStub(TEST_URL_JPG);
    }

}