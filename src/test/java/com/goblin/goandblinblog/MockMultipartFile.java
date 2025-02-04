package com.goblin.goandblinblog;

import org.springframework.web.multipart.MultipartFile;

public class MockMultipartFile {

    private MockMultipartFile() {
    }

    public static MultipartFile getMockMultipartFile(String fileName, String originalFilename, String contentType, byte[] content) {
        return new org.springframework.mock.web.MockMultipartFile(
                fileName, originalFilename, contentType, content
        );
    }

}
