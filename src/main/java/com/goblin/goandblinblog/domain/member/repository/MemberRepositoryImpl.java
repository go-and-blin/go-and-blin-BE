package com.goblin.goandblinblog.domain.member.repository;

import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.global.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member findById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member findByNickname(String nickName) {
        return memberJpaRepository.findByNickName(nickName)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public boolean existsByNickname(String nickName) {
        return memberJpaRepository.existsByNickName(nickName);
    }

    @Override
    public void deleteAllInBatch() {
        memberJpaRepository.deleteAllInBatch();
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

}