package com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.Session;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SessionRepository extends JpaRepository<Session,Long> {

    List<Session> findByUser (User user);

    Optional<Session> findByRefreshToken(String refreshToken);

    Optional<Session> deleteSessionByRefreshToken(String refreshToken);
}
