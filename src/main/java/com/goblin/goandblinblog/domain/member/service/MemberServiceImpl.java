package com.goblin.goandblinblog.domain.member.service;

import com.goblin.goandblinblog.domain.member.controller.port.MemberService;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.dto.response.MemberResponse;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.global.exception.member.MemberAlreadyExistsException;
import com.goblin.goandblinblog.global.storage.provider.StorageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final StorageProvider storageProvider;

    @Transactional
    @Override
    public void updateProfilesNickname(String nickname, Long memberId) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new MemberAlreadyExistsException();
        }

        Member memberByMemberId = memberRepository.findById(memberId);
        memberByMemberId.updateNickName(nickname);
    }

    @Transactional
    @Override
    public MemberResponse updateProfileImage(MultipartFile file, Long memberId) {
        String profileImageURL = storageProvider.uploadProfileImage(file);
        Member memberByMemberId = memberRepository.findById(memberId);
        memberByMemberId.updateProfileImage(profileImageURL);

        return MemberResponse.of(memberByMemberId);
    }

}