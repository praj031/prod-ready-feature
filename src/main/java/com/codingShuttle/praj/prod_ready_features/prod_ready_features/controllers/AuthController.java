package com.codingShuttle.praj.prod_ready_features.prod_ready_features.controllers;


import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.LoginDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.LoginResponseDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.SignUpDTO;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.dto.UserDto;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.AuthService;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    //private final UserRepository userRepository;
    @Autowired
    private final UserService userService;
    private final AuthService authService;
    //private final AuthService authService;

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

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        request.getCookies();


        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest httpServletRequest){
        String refreshToken = Arrays.stream(httpServletRequest.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies") );

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }



}
