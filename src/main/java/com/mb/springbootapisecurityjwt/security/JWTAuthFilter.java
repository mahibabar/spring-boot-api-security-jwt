package com.mb.springbootapisecurityjwt.security;

import com.mb.springbootapisecurityjwt.service.MyUserDetailsService;
import com.mb.springbootapisecurityjwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;

    @Autowired
    public JWTAuthFilter(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // Check if Authorization is present in the request
        // Get JWT token from Authorization header in the request
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;
        if (authorizationHeader != null && authorizationHeader.contains("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = JWTUtil.extractUserNameFromToken(token);
        }

        // get user details and validate against token
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            Boolean validToken = JWTUtil.isValidToken(token, userDetails);
            if (validToken) {
                UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                jwtAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            }
        }

        // continue filter chain
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
