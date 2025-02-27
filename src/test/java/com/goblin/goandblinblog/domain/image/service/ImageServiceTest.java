package com.goblin.goandblinblog.domain.image.service;

import static com.goblin.goandblinblog.MockMultipartFile.getMockMultipartFile;
import static org.assertj.core.api.Assertions.assertThat;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.TestS3Config;
import com.goblin.goandblinblog.domain.image.controller.dto.response.ImageResponse;
import com.goblin.goandblinblog.domain.image.controller.port.ImageService;
import com.goblin.goandblinblog.domain.image.entity.Image;
import com.goblin.goandblinblog.domain.image.service.port.ImageRepository;
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(TestS3Config.class)
class ImageServiceTest extends IntegrationTestSupport {

    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private IdentifierGenerator identifierGenerator;

    @DisplayName("이미지를 업로드하면 이미지 URL을 반환한다.")
    @Test
    void updateProfileImage() {
        // given
        String imageUrl = "imageUrl";

        Image image = new Image(imageUrl, identifierGenerator.generate());
        Image result = imageRepository.save(image);

        // when
        ImageResponse response = imageService.upload(
                getMockMultipartFile("file", "test-image.jpg", "image/jpeg", new byte[1024]), result.getPostId());

        // then
        assertThat(response)
                .extracting("postId", "imageUrl")
                .containsExactlyInAnyOrder(result.getPostId(), TestS3Config.TEST_URL_JPG);
    }

}