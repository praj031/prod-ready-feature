package com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Permissions;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.utils.PermissionMap;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Roles;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails { // Here we can see User details that helps us implements all the methods

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {Set<SimpleGrantedAuthority> permissions = PermissionMap.grantedAuthorityForRole(roles);
                    authorities.addAll(permissions);
                }

        );
        return authorities;

    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}