package com.itsyourblog.authservice.controller;

import com.itsyourblog.authservice.AuthserviceApplication;
import com.itsyourblog.authservice.dto.reponse.AuthResponse;
import com.itsyourblog.authservice.dto.request.LoginRequest;
import com.itsyourblog.authservice.dto.request.RegisterRequest;
import com.itsyourblog.authservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request)
    {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) throws Exception {
        return userService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "JWT Authentication Successful";
    }
}
