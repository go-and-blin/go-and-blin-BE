package com.goblin.goandblinblog.domain.image.controller;

import static com.goblin.goandblinblog.MockMultipartFile.getMockMultipartFile;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

class ImageControllerTest extends ControllerTestSupport {

    private final String BASE_URL = "/api/v1/images";

    @DisplayName("게시글 이미지를 업로드 한다.")
    @Test
    void uploadImage() throws Exception {
        MultipartFile mockFile = getMockMultipartFile("file", "test-image.jpg", "image/jpeg", new byte[1024]);

        String testId = "testId";
        mockMvc.perform(
                        multipart(POST, BASE_URL)
                                .file("file", mockFile.getBytes())
                                .param("postId", testId)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                ).andDo(print())
                .andExpect(status().isOk());
    }

}