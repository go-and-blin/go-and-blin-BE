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
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
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

    @Autowired
    private IdentifierGenerator identifierGenerator;

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
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);

        String result = postService.create(member.getId(), request);

        assertThat(id).isEqualTo(result);

    }

    @DisplayName("글을 수정한다")
    @Test
    void updatePost() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        postRepository.save(Post.create(request.id(), request.title(), request.content(), member, category));

        PostUpdateServiceRequest updateRequest = createUpdateRequest();

        String resultId = postService.update(id, updateRequest);
        Post result = postRepository.findById(resultId);

        assertThat(result).extracting(
                "title", "content"
        ).contains(updateRequest.title(), updateRequest.content());
    }

    @DisplayName("수정하려는 글이 존재하지 않는다면, PostNotFound 발생한다")
    @Test
    void updatePostWithPostNotFound() {
        String id = createId();

        assertThatThrownBy(
                () -> postService.update(id, createUpdateRequest()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("글을 삭제 한다")
    @Test
    void deletePost() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        Post save = postRepository.save(
                Post.create(request.id(), request.title(), request.content(), member, category));

        postService.delete(save.getId());

        assertThatThrownBy(
                () -> postRepository.findById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("삭제하려는 글이 없으면, PostNotFound 발생한다.")
    @Test
    void deletePostWithPostNotFound() {
        assertThatThrownBy(
                () -> postService.delete("test"))
                .isInstanceOf(PostNotFoundException.class);
    }

    private PostUpdateServiceRequest createUpdateRequest() {
        return new PostUpdateServiceRequest(
                "update title",
                "update content"
        );
    }

    private PostCreateServiceRequest createPostCreateRequest(String id) {
        return new PostCreateServiceRequest(id, "test", "test", category.getId());
    }

    private String createId() {
        return identifierGenerator.generate();
    }
}