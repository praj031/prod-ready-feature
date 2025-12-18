package com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor  // ← ADD THIS
@AllArgsConstructor
@Data
public class PostDTO {

    private Long id;

    private String title;

    private String description;

    //Jackson convert  the class to json, hence we need this  class.

}
