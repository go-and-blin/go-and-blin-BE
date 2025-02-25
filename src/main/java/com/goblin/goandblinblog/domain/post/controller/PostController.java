package com.goblin.goandblinblog.domain.post.controller;

import com.goblin.goandblinblog.domain.post.controller.dto.request.PostCreateRequest;
import com.goblin.goandblinblog.domain.post.controller.dto.request.PostUpdateRequest;
import com.goblin.goandblinblog.domain.post.controller.port.PostService;
import com.goblin.goandblinblog.domain.post.service.dto.response.PostInfoResponse;
import com.goblin.goandblinblog.domain.post.service.dto.response.PostPageResponse;
import com.goblin.goandblinblog.global.anootation.CurrentLoginMember;
import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;
    private final IdentifierGenerator generator;

    @PostMapping
    public ResponseEntity<String> createPost(
            @CurrentLoginMember Long memberId,
            @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(memberId, request.toService()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(
        @CurrentLoginMember Long memberId,
        @PathVariable String id,
        @RequestBody PostUpdateRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(memberId, id, request.toService()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @CurrentLoginMember Long memberId,
            @PathVariable String id
    ) {
        postService.delete(memberId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/draft-id")
    public ResponseEntity<String> getULID() {
        return ResponseEntity.status(HttpStatus.CREATED).body(generator.generate());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostInfoResponse> getPost(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> getPosts(
            @RequestParam(defaultValue = "") String cursor,
            @RequestParam(defaultValue = "10") long size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll(cursor, size));
    }
}