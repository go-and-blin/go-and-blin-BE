package com.goblin.goandblinblog.domain.comment.repository;

import com.goblin.goandblinblog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

}