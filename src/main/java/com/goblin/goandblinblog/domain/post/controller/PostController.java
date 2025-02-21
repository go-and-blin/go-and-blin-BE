package com.goblin.goandblinblog.domain.post.controller;

import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;
    private final IdentifierGenerator generator;

    @PostMapping("/draft-id")
    public ResponseEntity<String> getULID() {
        return ResponseEntity.status(HttpStatus.CREATED).body(generator.generate());
    }
}