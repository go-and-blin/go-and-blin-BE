package com.goblin.goandblinblog.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.domain.post.entity.Post;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostUpdateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import com.goblin.goandblinblog.global.exception.post.PostNotFoundException;
import java.util.UUID;
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

    Member member = null;
    Category category = null;

    @BeforeEach
    void setUp() {
        category = categoryRepository.save(Category.create(CategoryType.GO, "스프링 부트"));
        member = memberRepository.save(
                Member.builder()
                        .nickName("test")
                        .password("test")
                        .imageUrl("test")
                        .build()
        );
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
        PostCreateServiceRequest request = createPostCreateRequest(uuid);

        String id = postService.create(member.getId(), request);

        assertThat(id).isEqualTo(uuid);

    }

    @DisplayName("글을 수정한다")
    @Test
    void updatePost() {
        String uuid = String.valueOf(UUID.randomUUID());
        PostCreateServiceRequest request = createPostCreateRequest(uuid);
        postRepository.save(Post.create(request.uuid(), request.title(), request.content(), member, category));

        PostUpdateServiceRequest updateRequest = createUpdateRequest();

        String id = postService.update(uuid, updateRequest);
        Post result = postRepository.findById(id);

        assertThat(result).extracting(
                "title", "content"
        ).contains(updateRequest.title(), updateRequest.content());
    }

    @DisplayName("수정하려는 글이 존재하지 않는다면, PostNotFound 발생한다")
    @Test
    void updatePostWithPostNotFound() {
        String uuid = String.valueOf(UUID.randomUUID());

        assertThatThrownBy(
                () -> postService.update(uuid, createUpdateRequest()))
                .isInstanceOf(PostNotFoundException.class);
    }

    private PostUpdateServiceRequest createUpdateRequest() {
        return new PostUpdateServiceRequest(
                "update title",
                "update content"
        );
    }

    private PostCreateServiceRequest createPostCreateRequest(String uuid) {
        return new PostCreateServiceRequest(uuid, "test", "test", category.getId());
    }
}