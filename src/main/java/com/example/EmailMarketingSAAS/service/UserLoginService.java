package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.dto.LoginRequest;
import com.example.EmailMarketingSAAS.dto.LoginResponse;
import com.example.EmailMarketingSAAS.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class UserLoginService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtils;

    public LoginResponse authenticate(LoginRequest authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail().trim(),
                        authRequestDto.getPassword().trim())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String lToken = jwtUtils.generateToken(userDetail);
        return new LoginResponse(lToken);
    }
}
