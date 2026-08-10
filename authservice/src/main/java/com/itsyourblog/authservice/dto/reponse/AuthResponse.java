package com.itsyourblog.authservice.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private Long userId;
    private String username;
    private String email;
    private String token;
}
