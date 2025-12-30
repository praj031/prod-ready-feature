package com.codingShuttle.praj.prod_ready_features.prod_ready_features.controllers;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.*;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    //private final UserRepository userRepository;
    @Autowired
    private final UserService userService;
    private final AuthService authService;
    private SessionService sessionService;
    private JwtService jwtService;
    private SubscriptionService subscriptionService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDTO signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    //There is another use case lets say customer pases the cookie rather than the username or password.
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response){

        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);

        log.info("===== Login info logs to check the null token response =======");
        log.info("Email == "+loginDTO.getEmail());
        log.info("Value = "+loginDTO.getPassword());

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        //cookie.setPath("/");
        //cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);
        request.getCookies();
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request,
                                                    HttpServletResponse response){


        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies") );

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(HttpServletRequest request, HttpServletResponse response)
    {
        log.info("logout controller");
        Long userId=null;
        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(c -> "refreshToken".equals(c.getName()))
                    .findFirst()
                    .ifPresent(cookie -> {
                        sessionService.deleteSession(cookie.getValue());
                    });
            Optional<Cookie> cookie=Arrays.stream(request.getCookies())
                    .filter(c -> "refreshToken".equals(c.getName()))
                    .findFirst();
            userId=jwtService.getUserIdFromToken(cookie.get().getValue());
        }else{throw new AuthenticationServiceException("Not found refresh Token");}


        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure("production".equals(deployEnv));
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);

        return ResponseEntity.ok(
                LogoutResponseDto.builder().id(userId)
                        .message("Logged out successfully")
                        .build()
        );
    }


    @PostMapping("/subscription/buysubscription")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> buySubscription(@RequestBody SubscriptionDto subscriptionDto,HttpServletRequest request){
        log.info("buysubscriptioncontroller");
        UserDto userDto=subscriptionService.addSubscription(subscriptionDto);
        return ResponseEntity.ok(userDto);
    }

    //To delete user create.
    @DeleteMapping("/delete/{userId}")
    @Secured("USER_DELETE")
    public void deleteUser(@PathVariable Long userId){
        log.info("delete controller");
        userService.deleteUserById(userId);
    }


}
