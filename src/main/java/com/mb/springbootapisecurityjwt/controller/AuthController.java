package com.mb.springbootapisecurityjwt.controller;

import com.mb.springbootapisecurityjwt.dto.LoginRequest;
import com.mb.springbootapisecurityjwt.dto.LoginResponse;
import com.mb.springbootapisecurityjwt.service.MyUserDetailsService;
import com.mb.springbootapisecurityjwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MyUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(MyUserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // Authenticate user
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad credentials");
        }

        // get user details for userName in the request
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserName());
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bad credentials");
        }
        // Generate JWT for user
        String token = JWTUtil.generateToken(userDetails, null);
        // return JWT in the response
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}
