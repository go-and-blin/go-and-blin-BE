package com.goblin.goandblinblog.domain.member.repository;

import com.goblin.goandblinblog.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
