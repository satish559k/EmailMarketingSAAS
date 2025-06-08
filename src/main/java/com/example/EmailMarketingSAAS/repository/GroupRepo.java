package com.example.EmailMarketingSAAS.repository;

import com.example.EmailMarketingSAAS.dto.GroupRequest;
import com.example.EmailMarketingSAAS.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupRepo extends JpaRepository<Group, UUID> {
    public Group findByGroupName(String groupName);
}
