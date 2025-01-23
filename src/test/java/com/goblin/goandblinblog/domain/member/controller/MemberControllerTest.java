package com.goblin.goandblinblog.domain.member.controller;

import com.goblin.goandblinblog.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerTestSupport {

    private final String MEMBER_API = "/api/v1/members/";

    @DisplayName("닉네임을 수정한다")
    @Test
    void updateProfilesNicknames() throws Exception {
        // given
        String nickname = "newNickname";

        // expected
        mockMvc.perform(
                        put(MEMBER_API + "profiles/nicknames")
                                .param("nickname", nickname)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}