package com.codingShuttle.praj.prod_ready_features.prod_ready_features.utils;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Permissions;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Permissions.*;
import static com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Roles.*;

public class PermissionMap {

    private static final Map<Roles, Set<Permissions>> map = Map.of(

            USER, Set.of(POST_VIEW,USER_VIEW),
            CREATOR, Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE),
            ADMIN, Set.of(POST_CREATE,POST_VIEW,POST_DELETE,POST_UPDATE,USER_VIEW,USER_CREATE,USER_DELETE)

    );

    public static Set<SimpleGrantedAuthority> grantedAuthorityForRole(Set<Roles> roles) {
        return roles.stream()
                .flatMap(role -> map.getOrDefault(role, Set.of()).stream())
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }

}
/*
    POST_VIEW,
    POST_CREATE,
    POST_UPDATE,
    POST_DELETE,


    USER_VIEW,
    USER_CREATE,
    USER_UPDATE,
    USER_DELETE
 */