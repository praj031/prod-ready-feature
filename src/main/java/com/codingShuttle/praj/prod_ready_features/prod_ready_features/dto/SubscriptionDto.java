package com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.enums.Subscription;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDto {

    @Enumerated(EnumType.STRING)
    private Subscription subscription;

}
