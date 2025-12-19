package com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Audited
public class PostEntity extends AuditableEntities{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @PrePersist
    void beforeSave() {

    }

    @PreUpdate
    void beforeUpdate() {

    }

    @PreRemove
    void beforeDelete() {

    }


}