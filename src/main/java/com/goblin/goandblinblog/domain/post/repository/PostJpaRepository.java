package com.goblin.goandblinblog.domain.post.repository;

import com.goblin.goandblinblog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

}
