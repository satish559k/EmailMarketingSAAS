package com.example.EmailMarketingSAAS.controller;

import com.example.EmailMarketingSAAS.config.SecurityConfig;
import com.example.EmailMarketingSAAS.dto.GlobalApiResponse;
import com.example.EmailMarketingSAAS.dto.UserRequest;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import com.example.EmailMarketingSAAS.service.UserRegisterService;
import com.example.EmailMarketingSAAS.util.GlobalResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("open")
@Slf4j
@AllArgsConstructor
public class UserRegisterController {
    private final UserRegisterService userRegisterService;
    private final SecurityConfig securityConfig;

    @PostMapping("new-register")
    public ResponseEntity<GlobalApiResponse<Object>> CreateUser(@RequestBody UserRequest user) {
        try{
            boolean isUserExists = userRegisterService.CheckUserExists(user);
            if (isUserExists) {
                return GlobalResponse.success(HttpStatus.OK.value(), "User Already Exists", user.getEmail());
            }
            User newUser = userRegisterService.registerUser(user);
            return GlobalResponse.success(HttpStatus.OK.value(), "Success", newUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            return GlobalResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

}
