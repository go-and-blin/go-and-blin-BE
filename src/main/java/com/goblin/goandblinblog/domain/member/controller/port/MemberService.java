package com.goblin.goandblinblog.domain.member.controller.port;

import com.goblin.goandblinblog.domain.member.service.dto.response.MemberResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void updateProfilesNickname(String nickname, Long memberId);

    MemberResponse updateProfileImage(MultipartFile file, Long memberId);

}