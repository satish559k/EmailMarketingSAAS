package com.example.EmailMarketingSAAS.service;

import com.example.EmailMarketingSAAS.dto.UserRequest;
import com.example.EmailMarketingSAAS.entity.User;
import com.example.EmailMarketingSAAS.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepo userRepo;

    public User registerUser(UserRequest user) {
        try {
            User newUser = new User();
            newUser.setEmail(user.getEmail().trim());
            newUser.setName(user.getName().toLowerCase(Locale.ROOT));
            newUser.setPassword(user.getPassword().trim());
            newUser.setRoles(user.getRoles());
            userRepo.save(newUser);
            return newUser;

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
