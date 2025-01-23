package com.goblin.goandblinblog.domain.member.service;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.global.exception.member.MemberAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MemberServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("새로운 닉네임을 받아 기존의 닉네임을 수정한다.")
    @Test
    void updateProfilesNickname() {
        // given
        String newNickname = "newNickname";
        Member member = getMember("password", "imageUrl", "goblin");
        Member savedMember = memberRepository.save(member);

        // when
        memberService.updateProfilesNickname(newNickname, savedMember.getId());

        // then
        Member updateMember = memberRepository.findById(savedMember.getId());

        assertThat(updateMember)
                .extracting("id", "nickName")
                .containsExactlyInAnyOrder(savedMember.getId(), newNickname);
    }

    @DisplayName("중복된 닉네임이 존재할 경우 예외가 발생한다.")
    @Test
    void updateProfilesNicknameWithDuplicatedNickname() {
        // given
        String newNickname = "goblin";
        Member member = getMember("password", "imageUrl", "goblin");
        Member savedMember = memberRepository.save(member);

        // expected
        assertThatExceptionOfType(MemberAlreadyExistsException.class)
                .isThrownBy(
                        () -> memberService.updateProfilesNickname(newNickname, savedMember.getId())
                );
    }

    private Member getMember(String password, String imageUrl, String goblin) {
        return Member.builder()
                .password(password)
                .imageUrl(imageUrl)
                .nickName(goblin)
                .build();
    }

}