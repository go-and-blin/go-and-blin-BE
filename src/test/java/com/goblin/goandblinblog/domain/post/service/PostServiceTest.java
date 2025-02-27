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
import com.goblin.goandblinblog.domain.post.service.dto.response.PostInfoResponse;
import com.goblin.goandblinblog.domain.post.service.dto.response.PostPageResponse;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import com.goblin.goandblinblog.global.exception.post.PostNotFoundException;
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

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
        postRepository.save(createPost(request));

        PostUpdateServiceRequest updateRequest = createUpdateRequest();

        String resultId = postService.update(member.getId(), id, updateRequest);
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
                () -> postService.update(member.getId(), id, createUpdateRequest()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("올바르지 않은 유저가 글을 수정하려고 하면, AccessDeniedException 발생한다.")
    @Test
    void updatePostWithAccessDeniedException() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        PostUpdateServiceRequest updateRequest = createUpdateRequest();
        Post save = postRepository.save(createPost(request));

        assertThatThrownBy(
                () -> postService.update(99L, save.getId(), updateRequest))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("글을 삭제 한다")
    @Test
    void deletePost() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        Post save = postRepository.save(createPost(request));

        postService.delete(member.getId(), save.getId());

        assertThatThrownBy(
                () -> postRepository.findById(id))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("삭제하려는 글이 없으면, PostNotFound 발생한다.")
    @Test
    void deletePostWithPostNotFound() {
        assertThatThrownBy(
                () -> postService.delete(1L, "test"))
                .isInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("올바르지 않은 유저가 글을 삭제하려고 하면, AccessDeniedException 발생한다.")
    @Test
    void deletePostWithAccessDeniedException() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        Post save = postRepository.save(createPost(request));
        assertThatThrownBy(
                () -> postService.delete(2L, save.getId()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("전체글을 조회한다.")
    @Test
    void getPosts() {
        List<Post> posts = List.of(
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())),
                createPost(createPostCreateRequest(createId())));

        posts.stream().forEach(postRepository::save);

        PostPageResponse all = postService.findAll("", 10L);

        assertThat(all)
                .isNotNull()
                .extracting("posts")
                .asList()
                .hasSize(posts.size());
    }

    @DisplayName("단일 글을 조회한다.")
    @Test
    void getPost() {
        String id = createId();
        PostCreateServiceRequest request = createPostCreateRequest(id);
        postRepository.save(createPost(request));

        PostInfoResponse result = postService.findById(id);

        assertThat(result)
                .isNotNull()
                .extracting("id", "title", "content")
                .contains(id, request.title(), request.content());
    }

    private Post createPost(PostCreateServiceRequest request) {
        return Post.create(request.id(), request.title(), request.content(), request.thumbnail(), member, category);
    }

    private PostUpdateServiceRequest createUpdateRequest() {
        return new PostUpdateServiceRequest(
                "update title",
                "update content"
        );
    }

    private PostCreateServiceRequest createPostCreateRequest(String id) {
        return new PostCreateServiceRequest(id, "test", "test", "thumbnail", category.getId());
    }

    private String createId() {
        return identifierGenerator.generate();
    }
}