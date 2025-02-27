package com.goblin.goandblinblog.domain.image.controller.port;

import com.goblin.goandblinblog.domain.image.controller.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ImageResponse upload(MultipartFile file, String postId);
}