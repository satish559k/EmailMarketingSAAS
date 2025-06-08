package com.example.EmailMarketingSAAS.scheduler;

import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.repository.EmailCampaignRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class EmailCampaignScheduler {
    EmailCampaignRepo emailCampaignRepo;

    @Scheduled(cron = "0 * * * * *")
    public void EmailScheduler() {

        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeLessThan(CampaignStatus.PENDING, LocalDateTime.now());

        for (EmailCampaign emailCampaign : emailCampaigns) {
            
        }

    }
}
