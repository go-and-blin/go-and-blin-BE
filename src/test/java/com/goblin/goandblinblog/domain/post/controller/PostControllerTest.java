package com.goblin.goandblinblog.domain.post.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class PostControllerTest extends ControllerTestSupport {

    private final String POST_API = "/api/v1/posts";

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