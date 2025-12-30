package com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Permissions;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String email;
    private String name;
    private String password;
    private Set<Roles> roles;
    private Set<Permissions> permissions;

}