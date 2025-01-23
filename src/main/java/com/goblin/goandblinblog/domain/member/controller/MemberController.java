package com.goblin.goandblinblog.domain.member.controller;

import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import com.goblin.goandblinblog.global.anootation.CurrentLoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/profiles/nicknames")
    public ResponseEntity<Void> updateProfilesNicknames(
            @RequestParam String nickname,
            @CurrentLoginMember Long memberId
    ) {
        memberService.updateProfilesNickname(nickname, memberId);

        return ResponseEntity.noContent().build();
    }

}