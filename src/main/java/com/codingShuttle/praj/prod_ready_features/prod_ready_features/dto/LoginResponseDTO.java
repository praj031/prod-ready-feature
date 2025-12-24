package com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDTO {

    private long id ;
    private String accessToken;
    private String refreshToken;

}
