package com.goblin.goandblinblog.domain.post.service;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    Long memberId = 1L;
    Long categoryId = 1L;

    @BeforeEach
    void setUp() {
        categoryId = categoryRepository.save(Category.create(CategoryType.GO, "스프링 부트")).getId();
        memberId = memberRepository.save(
                Member.builder()
                        .nickName("test")
                        .password("test")
                        .imageUrl("test")
                        .build()
        ).getId();
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("글을 생성한다.")
    @Test
    void createPost() {
        String uuid = String.valueOf(UUID.randomUUID());

        PostCreateServiceRequest request = new PostCreateServiceRequest(uuid, "test", "test", categoryId);

        String id = postService.create(memberId, request);

        Assertions.assertThat(id).isEqualTo(uuid);

    }
}