package com.codingShuttle.praj.prod_ready_features.prod_ready_features.controllers;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.PostDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.services.PostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private final PostServices postServices;

    @GetMapping(path = "/getAllPost")
    public List<PostDTO> getAllPost(){
        return postServices.getAllPosts();
    }

    @GetMapping(path = "/getByID/{postId}")
    public PostDTO getById(@PathVariable Long postId) {
        return postServices.getPostByID(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
    }


    @PostMapping(path = "/createPostDTO")
    public PostDTO  createPostDTO(@RequestBody PostDTO inputPost){
        return postServices.createPostDTO(inputPost);
    }


}
