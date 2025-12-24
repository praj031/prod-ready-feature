package com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    //This will find the user by it username--email we enter.


}
