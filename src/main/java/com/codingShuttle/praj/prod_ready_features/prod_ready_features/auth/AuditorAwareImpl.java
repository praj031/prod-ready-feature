package com.codingShuttle.praj.prod_ready_features.prod_ready_features.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
//        get security context
//        get authentication
//        get the principle
//        get the username
        return Optional.of("Pritish Raj");

        //Method should return the auditor s
    }
}
