package com.itsyourblog.trip_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "ZmM0ZjE0OTNkZjRlZDAxYjQ2MzgxOWE2Njk3ZjE2YzYwY2Q5NzMwNDU0NjUxYzQ4YjY3YjMzYzVhYzQ2Yg==";
    public SecretKey getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String exctractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token)
    {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver)
    {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean isTokenValid(String token)
    {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

}
