package com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    //This is where we write queries to be fetched from the database


}
