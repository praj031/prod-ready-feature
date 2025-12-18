package com.codingShuttle.praj.prod_ready_features.prod_ready_features.services;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface PostServices {

    List<PostDTO> getAllPosts();
    PostDTO createPostDTO(PostDTO postDTO);
    Optional<PostDTO> getPostByID(Long postId);

}
