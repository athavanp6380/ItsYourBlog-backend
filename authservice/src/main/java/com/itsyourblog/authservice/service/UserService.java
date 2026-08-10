package com.itsyourblog.authservice.service;

import com.itsyourblog.authservice.dto.reponse.AuthResponse;
import com.itsyourblog.authservice.dto.request.LoginRequest;
import com.itsyourblog.authservice.dto.request.RegisterRequest;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request) throws Exception;
}
