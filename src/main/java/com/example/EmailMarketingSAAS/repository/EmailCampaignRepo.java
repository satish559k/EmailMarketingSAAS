package com.example.EmailMarketingSAAS.repository;

import com.example.EmailMarketingSAAS.dto.EmailCampaignWithoutBodyDto;
import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailCampaignRepo extends JpaRepository<EmailCampaign, UUID> {
    // 1. Find all campaigns created by a specific user
    //List<EmailCampaign> findById(UUID id);

    // 2. Find campaigns by status
    List<EmailCampaign> findByStatus(CampaignStatus status);

    Optional<EmailCampaign> findById(UUID id);

    Optional<EmailCampaign> findByUser_IdAndGroup_Id(UUID userid , UUID groupid);

    @Query("SELECT COUNT(c) FROM EmailCampaign c WHERE c.user.id = :userId AND c.group.id = :groupId")
    long countCampaignByUserIdAndGroupId(@Param("userId") UUID userId, @Param("groupId") UUID groupId);


    List<EmailCampaign> findByStatusAndScheduledTimeLessThan(CampaignStatus status, LocalDateTime now);

    List<EmailCampaign> findByStatusAndScheduledTimeBetween(CampaignStatus status, LocalDateTime start, LocalDateTime end);
}
