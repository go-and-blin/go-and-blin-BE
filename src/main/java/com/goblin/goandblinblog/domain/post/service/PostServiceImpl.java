package com.goblin.goandblinblog.domain.post.service;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.domain.member.service.port.MemberRepository;
import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.domain.post.entity.Post;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostUpdateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public String create(Long memberId, PostCreateServiceRequest request) {
        Member member = memberRepository.findById(memberId);
        Category category = categoryRepository.findById(request.categoryId());
        return postRepository.save(
                Post.create(request.uuid(), request.title(), request.content(), member, category)
        ).getId();
    }

    @Transactional
    @Override
    public String update(String uuid, PostUpdateServiceRequest updateRequest) {
        Post post = postRepository.findById(uuid);
        post.update(updateRequest.title(), updateRequest.content());

        return post.getId();
    }
}