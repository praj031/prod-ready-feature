package com.codingShuttle.praj.prod_ready_features.prod_ready_features.service;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.Session;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
    private final JwtService jwtService;


    public void generateNewSession(User user, String refreshToken){

        List<Session> userSession = sessionRepository.findByUser(user);
        int session_limit = user.getSESSION_COUNT();
        if(userSession.size()==session_limit){
            userSession.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentUsedSession = userSession.getFirst();
            sessionRepository.delete(leastRecentUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);

    }

    /*
    Over here we are creating a limit of session.
    IF the limit has exceeded, we are deleting the last active session
    if the new login is there, we are creating a new session.
     */

    public void validateSession(String refeshToken){

        Session session = sessionRepository.findByRefreshToken(refeshToken).orElseThrow(
                () -> new SessionAuthenticationException("Session Not found for refresh token "+refeshToken));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void deleteSession(String refreshToken) {
        log.info("Attempting delete for refreshToken = {}", refreshToken);

        Optional<Session> session =
                sessionRepository.findByRefreshToken(refreshToken);
        log.info("Session = "+session);
        log.info("Session found = {}", session.isPresent());

        session.orElseThrow(() ->
                new SessionAuthenticationException(
                        "Session not found for refreshToken: " + refreshToken
                )
        );

        sessionRepository.delete(session.get());
    }

}
