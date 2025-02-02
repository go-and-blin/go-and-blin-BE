package com.goblin.goandblinblog.global.storage.config;

import com.goblin.goandblinblog.external.s3.provider.S3StorageProvider;
import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Bean
    public StorageProvider storageProvider(S3Client s3Client) {
        return new S3StorageProvider(s3Client);
    }

}