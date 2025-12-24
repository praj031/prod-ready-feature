package com.codingShuttle.praj.prod_ready_features.prod_ready_features.filters;

import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.entites.User;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.JwtService;
import com.codingShuttle.praj.prod_ready_features.prod_ready_features.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { //We need to take in all the request and we will authenticate

    private final JwtService jwtService;

    private final UserService userService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);//It will move to next filter
            return;
        }
            String token = requestTokenHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromTokan(token);

            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){  //Only do thi if I don't have authentication set inside the authentication holder.
                User user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //Adding the user in spring security context holder
            }

            filterChain.doFilter(request,response);

    }
}
