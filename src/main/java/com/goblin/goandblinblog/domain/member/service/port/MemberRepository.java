package com.goblin.goandblinblog.domain.member.service.port;

import com.goblin.goandblinblog.domain.member.entity.Member;

public interface MemberRepository {

    Member findById(Long memberId);

    Member findByNickname(String nickname);

}