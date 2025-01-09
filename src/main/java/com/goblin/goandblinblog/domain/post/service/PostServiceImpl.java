package com.goblin.goandblinblog.domain.post.service;

import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

}