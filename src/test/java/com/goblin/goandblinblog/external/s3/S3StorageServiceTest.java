package com.goblin.goandblinblog.external.s3;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.TestS3Config;
import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static com.goblin.goandblinblog.TestS3Config.TEST_URL_JPG;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Import(TestS3Config.class)
class S3StorageServiceTest extends IntegrationTestSupport {

    @Autowired
    private StorageProvider storageProvider;

    @DisplayName("프로필 이미지 업로드에 성공하면 파일 URL을 반환한다.")
    @Test
    void uploadProfileImage() {
        // given
        MultipartFile mockFile = getMockMultipartFile();

        // when
        String result = storageProvider.uploadProfileImage(mockFile);

        // then
        assertThat(result).isEqualTo(TEST_URL_JPG);
    }

    private MultipartFile getMockMultipartFile() {
        String originalFilename = "test-image.jpg";
        return new MockMultipartFile(
                "file", originalFilename, "image/jpeg", new byte[1024]
        );
    }

}