package com.example.EmailMarketingSAAS.dto;

import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCampaignWithoutBodyDto {
    private UUID id;
    private String subject;
    private LocalDateTime scheduledTime;
    private CampaignStatus status;
    private UUID userId;
    private UUID groupId;
}
