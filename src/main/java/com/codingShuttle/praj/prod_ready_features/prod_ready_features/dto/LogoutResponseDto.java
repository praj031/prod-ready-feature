package com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LogoutResponseDto {

    Long id;
    String message;

}
