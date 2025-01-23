package com.goblin.goandblinblog.domain.member.service;

import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.global.exception.member.MemberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void updateProfilesNickname(String nickname, Long memberId) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new MemberAlreadyExistsException();
        }

        Member memberByMemberId = memberRepository.findById(memberId);
        memberByMemberId.updateNickName(nickname);
    }

}