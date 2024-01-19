package com.movieapp.movieApi.server;

import com.movieapp.movieApi.model.*;
import com.movieapp.movieApi.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {


    private final UserRepo repo;
    private final PasswordEncoder encoder;
    private final JWT_Services services;
    private final AuthenticationManager authenticationManager;
    public RequestResponse register(RegisterUser registerUser) {
        User user = User.builder()
                .fullName(registerUser.getFullName())
                .email(registerUser.getEmail())
                .userRole(registerUser.getRole().equalsIgnoreCase(Role.User.name())? Role.User: Role.Admin)
                .password(encoder.encode(registerUser.getPassword()))
                .build();
        repo.save(user);
        String token =services.generateToken(user);
        return RequestResponse.builder()
                .token(token)
                .build();
    }

    public RequestResponse loginUser(LoginUser loginUser) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        User user = repo.findByEmail(loginUser.getEmail()).orElseThrow();

        String token =services.generateToken(user);
        return RequestResponse.builder()
                .token(token)
                .build();
    }
}
