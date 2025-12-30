package com.codingShuttle.praj.prod_ready_features.prod_ready_features.service;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.SubscriptionDto;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.UserDto;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    public UserDto addSubscription(SubscriptionDto subscriptionDto) {
        log.info("subscription service");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("securitycontextholder issue");
        user.setSubscription(subscriptionDto.getSubscription().toString());
        log.info(subscriptionDto.getSubscription().toString());
        return modelMapper.map(userService.saveUser(user),UserDto.class);
    }

}
