package com.movieapp.movieApi.Controllers;


import com.movieapp.movieApi.model.LoginUser;
import com.movieapp.movieApi.model.RegisterUser;
import com.movieapp.movieApi.model.RequestResponse;
import com.movieapp.movieApi.server.AuthenticationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class MainPageController {

    private final AuthenticationServices authServices;


    @PostMapping("/registration")
    public ResponseEntity<RequestResponse> RegisterUser(@RequestBody RegisterUser registerUser){
        return ResponseEntity.ok(authServices.register(registerUser));
    }

    @PostMapping("/login")
    public ResponseEntity<RequestResponse> RegisterUser(@RequestBody LoginUser loginUser){
        return ResponseEntity.ok(authServices.loginUser(loginUser));
    }
}
