package com.codingShuttle.praj.prod_ready_features.prod_ready_features.service;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);

    void deleteById(Long postID);
}
