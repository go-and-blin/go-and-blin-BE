package com.goblin.goandblinblog.domain.member.repository;

import com.goblin.goandblinblog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(String nickname);

    boolean existsByNickName(String nickName);

}