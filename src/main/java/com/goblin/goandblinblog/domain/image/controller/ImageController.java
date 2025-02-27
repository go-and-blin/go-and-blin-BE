package com.goblin.goandblinblog.domain.image.controller;

import com.goblin.goandblinblog.domain.image.controller.dto.response.ImageResponse;
import com.goblin.goandblinblog.domain.image.controller.port.ImageService;
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@RestController
public class ImageController {

    private final ImageService imageService;
    private final IdentifierGenerator generator;

    @PostMapping
    public ResponseEntity uploadImage(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "postId") String postId
    ) {
        ImageResponse upload = imageService.upload(file, postId);
        return ResponseEntity.ok(upload);
    }

}