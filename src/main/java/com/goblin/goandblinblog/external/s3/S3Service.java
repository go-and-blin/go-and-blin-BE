package com.goblin.goandblinblog.external.s3;

import com.goblin.goandblinblog.global.exception.file.S3FileUploadException;
import com.goblin.goandblinblog.global.validator.ImageFileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadProfileImage(MultipartFile file) {
        ImageFileValidator.validate(file);
        String fileName = generateFileName(file.getOriginalFilename());
        uploadFileToS3(file, fileName);

        return getFileURL(fileName);
    }

    private void uploadFileToS3(MultipartFile file, String fileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            log.error("S3Service.uploadFile : {}", e.getMessage());
            throw new S3FileUploadException();
        }
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private String generateFileName(String originalFilename) {
        return UUID.randomUUID() + getExtension(originalFilename);
    }

    private String getFileURL(String fileName) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder().bucket(bucketName).key(fileName).build();
        return String.valueOf(s3Client.utilities()
                .getUrl(getUrlRequest)
        );
    }

}