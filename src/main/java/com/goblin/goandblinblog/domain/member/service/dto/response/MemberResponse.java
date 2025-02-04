package com.goblin.goandblinblog.domain.member.service.dto.response;

import com.goblin.goandblinblog.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberResponse(

        Long memberId,
        String nickname,
        String imageUrl

) {

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickName())
                .imageUrl(member.getImageUrl())
                .build();
    }

}