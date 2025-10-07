package com.example.EmailMarketingSAAS.scheduler;

import com.example.EmailMarketingSAAS.entity.EmailCampaign;
import com.example.EmailMarketingSAAS.enums.CampaignStatus;
import com.example.EmailMarketingSAAS.repository.EmailCampaignRepo;
import com.example.EmailMarketingSAAS.service.EmailSenderService;
import com.example.EmailMarketingSAAS.service.RedisService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Component
@Transactional
public class EmailCampaignScheduler {
    private final EmailCampaignRepo emailCampaignRepo;
    private final EmailSenderService emailSenderService;
    private final RedisService redisService;
    private final RedisTemplate<String, Object> redisTemplate;

//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(fixedRate = 20000)
    public void EmailScheduler() throws MessagingException {
        System.out.println("Email Scheduler started");

        Map<Object,Object> emailCampaignData =redisService.getValueHash("email:"+LocalDateTime.now().toLocalDate());
        emailCampaignData.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            LocalDateTime secheduleTime = LocalDateTime.parse(entry.getValue().toString());
            if(LocalDateTime.now().isAfter(secheduleTime)){
                Optional<EmailCampaign> emailCampaigns = emailCampaignRepo.findById(UUID.fromString(entry.getKey().toString()));
                try {
                    sendEmail(emailCampaigns.get());
                    System.out.println("sendEmail"+entry.getValue());
                    redisService.deleteHash("email:"+LocalDateTime.now().toLocalDate(),entry.getKey().toString());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

//        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeLessThan(CampaignStatus.PENDING,
//                LocalDateTime.now());
//
//        for (EmailCampaign emailCampaign : emailCampaigns) {
//            sendEmail(emailCampaign);
//        }

    }

//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedRate = 10000)
    public void RedisSchedule() throws MessagingException {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();                 // 2025-10-05 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); // 2025-10-05 23:59:59

        System.out.println("Redis Scheduled task running at " + LocalDateTime.now());
        List<EmailCampaign> emailCampaigns = emailCampaignRepo.findByStatusAndScheduledTimeBetween(CampaignStatus.PENDING,
                startOfDay,
                endOfDay);
        for (EmailCampaign emailCampaign : emailCampaigns) {
//            redisService.setValueWithTTL("email:"+emailCampaign.getId().toString(),emailCampaign.getScheduledTime().toString());
            redisService.setValueHashWithTTL("email:"+LocalDateTime.now().toLocalDate(),emailCampaign.getId().toString(),emailCampaign.getScheduledTime().toString());
        }
    }

//    in this cron remove DB call and add redis call every min
//    there will be one more cron run on 12:00am every day to
//    get data and store in redis,after data used delete the data from redis
//

    public void sendEmail(EmailCampaign emailCampaign) throws MessagingException {
        emailSenderService.sendEmailCampaign(emailCampaign);
    }
}
