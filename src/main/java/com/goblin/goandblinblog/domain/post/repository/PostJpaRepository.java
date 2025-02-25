package com.goblin.goandblinblog.domain.post.repository;

import com.goblin.goandblinblog.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<Post, String> {

    @Query("SELECT p FROM Post p join fetch p.member")
    Optional<Post> findById(String id);
}