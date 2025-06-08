package com.example.EmailMarketingSAAS.repository;

import com.example.EmailMarketingSAAS.entity.GroupOfEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupOfEmailRepo extends JpaRepository<GroupOfEmail, UUID> {
    List<GroupOfEmail> findBygroupId(UUID groupId);
}
