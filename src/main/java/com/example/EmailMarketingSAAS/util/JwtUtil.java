package com.example.EmailMarketingSAAS.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.EmailMarketingSAAS.dto.JwtTokenDto;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.mapper.JwtTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class JwtUtil {
    @Autowired
    private JwtTokenMapper jwtTokenMapper;

    public String generateToken(User user) {
        JwtTokenDto data = jwtTokenMapper.UserDetailsToJwtTokenDto(user);
        return JWT.create()
                .withSubject(data.getId().toString())
                .withClaim("role",data.getRoles().toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(604800))
                .sign(Algorithm.HMAC256("ertgyhjukijhtrtyui"));
    }
}
