package com.goblin.goandblinblog.global.storage.provider;

import org.springframework.web.multipart.MultipartFile;

public interface StorageProvider {

    String uploadProfileImage(MultipartFile file);

}