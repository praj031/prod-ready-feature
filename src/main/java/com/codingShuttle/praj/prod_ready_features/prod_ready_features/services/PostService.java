package com.codingShuttle.praj.prod_ready_features.prod_ready_features.services;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);

}
