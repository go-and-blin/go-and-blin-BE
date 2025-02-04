package com.goblin.goandblinblog.domain.member.controller;

import com.goblin.goandblinblog.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import static com.goblin.goandblinblog.MockMultipartFile.getMockMultipartFile;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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

    @DisplayName("MultipartFile을 받아 프로필 이미지를 업데이트한다.")
    @Test
    void updateProfilesImage() throws Exception {
        // given
        MultipartFile mockFile = getMockMultipartFile("file", "test-image.jpg", "image/jpeg", new byte[1024]);
        Long memberId = 1L;

        // expected
        mockMvc.perform(
                        multipart(PUT, MEMBER_API + "profiles/image")
                                .file("file", mockFile.getBytes())
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .requestAttr("CurrentLoginMember", memberId)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("프로필 이미지 업데이트 시 파일이 null이면 예외를 발생한다.")
    @Test
    void updateProfilesImageWithNullFile() throws Exception {
        // given
        Long memberId = 1L;

        // expected
        mockMvc.perform(
                        multipart(PUT, MEMBER_API + "profiles/image")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .requestAttr("CurrentLoginMember", memberId)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}