package com.goblin.goandblinblog.domain.post.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import com.goblin.goandblinblog.domain.post.service.dto.response.PostInfoResponse;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class PostControllerTest extends ControllerTestSupport {

    private final String POST_API = "/api/v1/posts";

    @DisplayName("단일 글을 조회한다.")
    @Test
    void getPost() throws Exception{

        String postId = "1";
        PostInfoResponse postInfoResponse = new PostInfoResponse(postId, "test", "test", "test", LocalDateTime.now());

        when(postService.findById(postId)).thenReturn(postInfoResponse);

        mockMvc.perform(
                get(POST_API + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("전체 글을 조회한다.")
    @Test
    void getPosts() throws Exception {
        mockMvc.perform(
                get(POST_API)
                        .param("cursor", "")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("ULID를 생성한다.")
    @Test
    void createDraftId() throws Exception {
        mockMvc.perform(
                        post(POST_API + "/draft-id")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }
}