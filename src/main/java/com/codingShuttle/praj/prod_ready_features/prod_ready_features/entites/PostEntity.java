package com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.generate.GeneratedMethod;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;


}
