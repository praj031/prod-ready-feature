package com.codingShuttle.praj.prod_ready_features.prod_ready_features.Impl;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.PostDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.PostEntity;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.exception.ResourceNotFoundException;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories.PostRepository;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.services.PostServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostServices {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    //This service will get us all the value from the repository via GET Request
    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(
                postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .collect(Collectors.toList());
    }

    //This service will help me insert the value inside the DB via POST request
    @Override
    public PostDTO createPostDTO(PostDTO inputPost) {

        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity),PostDTO.class);
    }

    //This service me return me post by it's ID/postId.
    @Override
    public Optional<PostDTO> getPostByID(Long postId){
        PostEntity postEntity =  postRepository.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("ID Not Found : "+postId));
        return Optional.ofNullable(modelMapper.map(postEntity, PostDTO.class));
    }

}
