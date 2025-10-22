package com.example.EmailMarketingSAAS.controller;

import com.example.EmailMarketingSAAS.dto.LoginRequest;
import com.example.EmailMarketingSAAS.dto.LoginResponse;
import com.example.EmailMarketingSAAS.service.UserLoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("open")
public class UserLoginController {
    private final UserLoginService loginService;

    @PostMapping("/login")
    public LoginResponse Login (@RequestBody LoginRequest authRequestDto) {
        return loginService.authenticate(authRequestDto);
    }
}
