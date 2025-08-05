package com.example.EmailMarketingSAAS.scheduler;

import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.repository.EmailCampaignRepo;
import com.example.EmailMarketingSAAS.service.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
@Transactional
public class EmailCampaignScheduler {
    EmailCampaignRepo emailCampaignRepo;
    EmailSenderService emailSenderService;

    @Scheduled(cron = "0 * * * * *")
    public void EmailScheduler() throws MessagingException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeLessThan(CampaignStatus.PENDING,
                LocalDateTime.now());

        for (EmailCampaign emailCampaign : emailCampaigns) {
            sendEmail(emailCampaign);
        }

    }

    public void sendEmail(EmailCampaign emailCampaign) throws MessagingException {
        emailSenderService.sendEmailCampaign(emailCampaign);
    }
}
