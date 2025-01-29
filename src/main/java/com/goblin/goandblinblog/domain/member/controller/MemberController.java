package com.goblin.goandblinblog.domain.member.controller;

import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import com.goblin.goandblinblog.domain.member.service.dto.response.MemberResponse;
import com.goblin.goandblinblog.global.anootation.CurrentLoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/profiles/image")
    public ResponseEntity<MemberResponse> updateProfilesImage(
            @RequestPart MultipartFile file,
            @CurrentLoginMember Long memberId
    ) {
        MemberResponse memberResponse = memberService.updateProfileImage(file, memberId);

        return ResponseEntity.ok(memberResponse);
    }

}