package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.config.SecurityConfig;
import com.example.EmailMarketingSAAS.dto.UserRequest;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final SecurityConfig securityConfig;

    public User registerUser(UserRequest user) {
        try {
            User newUser = new User();
            String encodedPass = passwordEncoder.encode(user.getPassword().trim());
            newUser.setEmail(user.getEmail().trim());
            newUser.setName(user.getName().toLowerCase(Locale.ROOT));
            newUser.setPassword(encodedPass);
            newUser.setRoles(user.getRoles());
            userRepo.save(newUser);
            return newUser;

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public User loginUser(UserRequest user) {
        User userDataByUserName = userRepo.findByName(user.getEmail().trim());
        boolean isValisUser = securityConfig.passwordEncoder().matches(user.getPassword().trim(),userDataByUserName.getPassword());
        if (isValisUser) {
            //generate token
        }
        //return user login with token
    }

}
