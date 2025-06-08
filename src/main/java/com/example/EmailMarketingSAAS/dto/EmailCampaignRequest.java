package com.example.EmailMarketingSAAS.dto;

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
public class EmailCampaignRequest {
    private LocalDateTime scheduledTime;
    private String subject;
    private String body; //This will be html
    private String userName;
    private UUID groupId;
}
