package com.example.EmailMarketingSAAS.repository;

import com.example.EmailMarketingSAAS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    public User findByName(String Username);

    public User findByEmail(String emailId);
}
