package com.codingShuttle.praj.prod_ready_features.prod_ready_features.handlers;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.JwtService;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User =(DefaultOAuth2User) token.getPrincipal();

        //log.info(oAuth2User.getAttributes("email"));
        log.info("Email before capture: {}", oAuth2User.getAttributes().get("email"));

        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        log.info("Email After capture : {}", email);

        User user = (User) userService.getUsrByEmail(email); // Her we get user email from the user service


        //Here we are checking if the user is not there we can create a new user for login
        if (user == null){
            User createUser = User.builder()
                            .name(name)
                            .email(email)
                            .password("pass")
                            .build();
            user = userService.saveUser(createUser);
        }

        String accessToken =  jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:8083/WelcomeHome.html?token="+accessToken;
        getRedirectStrategy().sendRedirect(request,response,frontEndUrl);
        ///  src/main/resources/static/WelcomeHome.html



    }

}
